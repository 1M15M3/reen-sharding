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

        //处理explain
        SqlType sqlType = SqlTypeUtil.getSqlType(sql);
        if (sqlType.EXPLAIN == sqlType){

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
