package exception;

import jdbc.model.ExecuteCommand;
import jdbc.model.ParameterCommand;
import jdbc.model.StatementCreateCommand;

import java.util.Map;

/**
 * 跟踪上下文
 * Created by aude on 2016/12/21.
 */
public class TraceContext {
    private String sql;
    private StatementCreateCommand createCommand;
    private ExecuteCommand executeCommand;
    private Map<Integer, ParameterCommand> parameterCommand;

    @Override
    public String toString() {
        return String.format("sql:{%s} createCommand:{%s} executeCommand:{%s} parameterCommand:{%s}",
                sql,
                createCommand !=null ? createCommand:"",
                executeCommand != null ? executeCommand:"",
                parameterCommand != null ? parameterCommand : "");
    }
}
