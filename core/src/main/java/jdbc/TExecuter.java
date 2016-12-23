package jdbc;

import exception.AudeException;
import exception.TraceContext;
import jdbc.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.model.SqlType;
import parser.utils.SqlTypeUtil;
import router.IRouteService;
import datasource.AudeDataSource;
import router.IRouteService;
import router.model.ExecutePlan;
import router.model.TargetSqlEntity;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aude on 2016/12/21.
 * <p>
 * sql最终执行的核心
 */
public class TExecuter {
    private final static Logger logger = LoggerFactory.getLogger(TExecuter.class);

    private IRouteService routeService;
    private AudeDataSource dataSource;
    private Map<DataSource, Connection> openedConnection;
    private TConnection currentConnection;

    public TExecuter(IRouteService routeService, AudeDataSource dataSource, Map<DataSource, Connection> openedConnection, TConnection currentConnection) {
        this.routeService = routeService;
        this.dataSource = dataSource;
        this.openedConnection = openedConnection;
        this.currentConnection = currentConnection;
    }

    public TExecuter(IRouteService routeService, AudeDataSource audeDataSource, Map<DataSource, Connection> openedConnection) {
    }

    public ResultSetHandler execute(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommand, TStatement outStmt) throws SQLException {
        TraceContext trace = new TraceContext();
        try {
            return execute(createCommand, executeCommand, parameterCommand, outStmt, trace);
        } catch (AudeException e) {
            throw new SQLException(e);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * @param createCommand       创建Statement的命令
     * @param executeCommand      执行Sql的命令
     * @param parameterCommandMap sql的参数
     * @param outStmt             应用层获取的Statement
     * @param trace               跟踪
     * @return
     */
    public ResultSetHandler execute(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommand, TStatement outStmt, TraceContext trace) throws SQLException {
        //参数
        boolean isPreparedStatement = createCommand.getMethod() != StatementCreateMethod.createStatement ? true : false;
        String sql = getSql(isPreparedStatement, outStmt, executeCommand);

        trace.setSql(sql);
        trace.setCreateCommand(createCommand);
        trace.setExecuteCommand(executeCommand);
        trace.setParameterCommand(parameterCommand);

        //路由
        ExecutePlan plan = routeService.doRoute(sql, parameterCommand);

        if (logger.isDebugEnabled()) {
            logger.debug("Aude execute SQL:" + plan.toString());
        }

        if (plan.getExecuteType() == null) {
            throw new SQLException("执行计划不正确" + plan.toString());
        }

        ResultSetHandler resultSetHandler = new ResultSetHandler();
        List<TargetSqlEntity> sqlList = plan.getSqlList();
        ArrayList<ResultSet> resultSet = new ArrayList<ResultSet>(sqlList.size());

        //清理
        outStmt.closeOpenedStatement();
        boolean resultType = false;

        //处理explain语句的情况
        SqlType sqlType = SqlTypeUtil.getSqlType(sql);
        if (sqlType.EXPLAIN == sqlType) {
            resultSet.add(new ExplainResultSet(plan));
            resultSetHandler.setResultSet(new IteratorResultSet(resultSet, outStmt, plan));
            setResultToStatement(true, outStmt, resultSetHandler);
            return resultSetHandler;
        }

        //真正的执行
        for (TargetSqlEntity target : sqlList) {
            Connection conn = getConnection(target);
            Statement targetStatement = getStatement(isPreparedStatement, createCommand, parameterCommand, plan.getOverrideParameters(), conn, target);
            ExecuteMethod method = executeCommand.getMethod();
            Object[] args = executeCommand.getArgs();
        }

    }

    /**
     *
     * @param isPreparedStatement
     * @param createCommand
     * @param parameterCommand
     * @param overrideParameters limit 解析过程中会产生需要覆盖的参数
     * @param conn
     * @param target
     * @return
     */
    private Statement getStatement(boolean isPreparedStatement, StatementCreateCommand createCommand, Map<Integer, ParameterCommand> parameterCommand, Map<Integer, Object> overrideParameters, Connection conn, TargetSqlEntity target) {
    }

    /**
     * 获取一个真正用于执行本次sql的connection
     * 思路是拿到targetSqlEntity中的partition,根据partition得到datasource实例,最后得到connection
     *
     * @param target
     * @return
     */
    private Connection getConnection(TargetSqlEntity target) throws SQLException {
        String targetPartition = target.getPartition();
        DataSource targetDataSource = targetPartition == null ? dataSource.getDefaultDatatSource() : dataSource.getDataSourceByName(targetPartition);
        if (targetDataSource == null) {
            throw new AudeException("没有找到对应的数据源实例,请检查配置:" + targetPartition);
        }
        Connection conn = openedConnection.get(targetDataSource);
        if (conn == null) {
            conn = targetDataSource.getConnection();
            conn.setAutoCommit(currentConnection.getAutoCommit());
            conn.setTransactionIsolation(currentConnection.getTransactionIsolation());
            openedConnection.put(targetDataSource, conn);
        }
        return conn;
    }

    /**
     * 把ResultSetHandler的值直接保存到Statement中,减少Statement对他的依赖
     *
     * @param trace
     * @param outStmt
     * @param resultSetHandler
     */
    private void setResultToStatement(boolean resultType, TStatement outStmt, ResultSetHandler resultSetHandler) {
        if (resultType) {
            outStmt.setCurrentResultSet(resultSetHandler.getResultSet());
        } else {
            outStmt.setCUrrentUpdateCount(resultSetHandler.getUpdateCount());
        }
    }


    /**
     * 获取原始sql
     *
     * @param isPreparedStatement
     * @param outStmt
     * @param executeCommand
     * @return
     */
    private String getSql(boolean isPreparedStatement, TStatement outStmt, ExecuteCommand executeCommand) {
        if (isPreparedStatement) {
            return ((TPreparedStatement) outStmt).getSql();
        } else {
            return (String) executeCommand.getArgs()[0];
        }
    }
}