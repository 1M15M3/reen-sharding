package parser.mysql;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat.Condition;

import parser.SqlParser;
import parser.model.ParseResult;
import parser.mysql.visitor.SqlVisitor;
import router.model.ExecutePlan;

import java.util.List;

/**
 * 抽象sql解析器
 * <p>
 * 用来实现SQLParser的三大方法:init,parse和changeSql
 * <p>
 * Created by aude on 2016/12/24.
 */
public class MySqlSqlParser implements SqlParser {

    protected SQLStatementParser parser;
    protected SqlVisitor visitor;
    protected SQLStatement statement;
    protected String sql;
    protected List<Object> parameters;

    @Override
    public void init(String sql, List<Object> objects) {
        this.parser = new MySqlStatementParser(sql);
        this.visitor = new SqlVisitor(parameters);
        this.parameters = parameters;
        this.sql = sql;
    }

    /**
     * 默认通过visitor解析,子类可以覆盖
     *
     * 限制:分表的where中,分表key只能出现一次且必须是 a=1 或者 a in ()的类型
     * @param result
     */
    @Override
    public void parse(ParseResult result) {

        statement = parsre.parseStatement();

        /**
         * 用visitor遍历sql
         */
        statement.accept(visitor);

        /**
         * 表名格式化
         */
        alisMapFix(result);

        /**
         * 原始sql
         */
        result.setSql(sql);



    }

    @Override
    public void changeSql(ParseResult result, ExecutePlan plan) {

    }

}
