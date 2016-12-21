package jdbc;

import exception.AudeException;
import exception.TraceContext;
import jdbc.model.*;
import router.IRouteService;
import datasource.AudeDataSource;
import router.IRouteService;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by aude on 2016/12/21.
 * <p>
 * sql最终执行的核心
 */
public class TExecuter {

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
    public ResultSetHandler execute(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommandMap, TStatement outStmt, TraceContext trace) {
        //参数
        boolean isPreparedStatement = createCommand.getMethod() != StatementCreateMethod.createStatement ? true : false;

    }
}
