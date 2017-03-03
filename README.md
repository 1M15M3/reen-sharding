## 说明

轻量级的分库分表中间件。可以和spring搭配使用。

## 图解



## 配置

主要是两个方面的配置：数据源和路由规则

数据源
（spring-db.xml)

```xml
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd
    http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.2.xsd 
    http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.2.xsd">

    <bean id="dataSource_p0" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="url">
            <value>
                ${jdbc.druid.url}
            </value>
        </property>
        <property name="username">
            <value>${jdbc.druid.user}</value>
        </property>
        <property name="password">
            <value>${jdbc.druid.password}</value>
        </property>
        <property name="filters" value="stat,wall"/>
    </bean>

    <bean id="dataSource_p1" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="url">
            <value>
                ${jdbc.druid.p2.url}
            </value>
        </property>
        <property name="username">
            <value>${jdbc.druid.p2.user}</value>
        </property>
        <property name="password">
            <value>${jdbc.druid.p2.password}</value>
        </property>
        <property name="filters" value="stat,wall"/>
    </bean>

    <!-- 数据源分发器 -->
    <bean id="multipleDataSource" class="com.aude.datasource.audeDataSource" init-method="init">
        <property name="dataSourceGroupSet">
            <set>
                <bean class="com.aude.datasource.DataSourceGroup">
                    <property name="identity" value="p0"/>
                    <property name="targetDataSource" ref="dataSource_p0"/>
                </bean>
                <bean class="com.aude.datasource.DataSourceGroup">
                    <property name="identity" value="p1"/>
                    <property name="targetDataSource" ref="dataSource_p1"/>
                </bean>
            </set>
        </property>
    </bean>

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation">
            <value>classpath:mybatis/quickstart-mybatis-configuration.xml</value>
        </property>
        <property name="mapperLocations">
            <value>classpath:mybatis/quickstart-tables/*.xml</value>
        </property>
        <property name="dataSource" ref="multipleDataSource"></property>
    </bean>

</beans>
```
对应的是core模块下的datasource包下的三个类：AudeDataSource，DataSourceDispatcher,DataSourceGroup

路由规则
（spring-aude.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:aude="http://aude.com/schema/aude-3.0"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
                           http://aude.com/schema/aude-3.0
                           http://aude.com/schema/aude-3.0.xsd">

    <aude:context/>

    <aude:table tableName="order" namePatten="order_{00}">
        <aude:columns>
            <!-- 根据 id 分表 -->
            <aude:column name="id"/>
        </aude:columns>
        <aude:function class="com.aude.quickstart.c1.VirtualModFunction4_4"/>
        <aude:nodeMapping class="com.aude.router.strategy.SimpleTableNodeMapping">
            <!-- p0 代表是一个数据源，与 multipleDataSource 定义的 identity 对应 -->

            <!-- 这个配置代表的是 p0 这个数据源里有 order_00 与 order_02 两个表 -->
            <aude:node>p0:00,02</aude:node>
            <!-- 这个配置代表的是 p1 这个数据源里有 order_01 与 order_03 两个表 -->
            <aude:node>p1:01,03</aude:node>
        </aude:nodeMapping>
    </aude:table>
</beans>
```