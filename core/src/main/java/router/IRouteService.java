package router;

import jdbc.model.ParameterCommand;
import router.model.ExecutePlan;

import java.util.Map;

/**
 * Created by aude on 2016/12/21.
 */
public interface IRouteService {
    public ExecutePlan doRoute(String sql, Map<Integer, ParameterCommand> parameterCommand);
}
