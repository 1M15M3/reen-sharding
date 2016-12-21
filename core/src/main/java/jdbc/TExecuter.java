package jdbc;

import jdbc.model.ExecuteCommand;
import jdbc.model.ResultSetHandler;
import router.IRouteService;
import datasource.AudeDataSource;
import jdbc.model.ParameterCommand;
import jdbc.model.StatementCreateCommand;
import router.IRouteService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

/**
 * Created by aude on 2016/12/21.
 */
public class TExecuter {
    public TExecuter(IRouteService routeService, AudeDataSource audeDataSource, Map<DataSource, Connection> openedConnection) {
    }

    public ResultSetHandler execute(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommand, TStatement stmt) {
    return null;
    }
}
