package router;

import jdbc.model.ParameterCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.SqlParser;
import parser.SqlParserFactory;
import parser.model.ParseResult;
import parser.model.SqlType;
import parser.utils.SqlTypeUtil;
import router.model.ExecutePlan;

import java.util.List;
import java.util.Map;

/**
 * Created by aude on 2016/12/21.
 */
public class RouteService implements IRouteService {
    private final Logger logger = LoggerFactory.getLogger(RouteService.class);

    /**
     * 路由入口
     *
     * @param sql              原始的sql语句
     * @param parameterCommand 业务层通过setString(1,"")等设置的参数
     * @return
     */
    @Override
    public ExecutePlan doRoute(String sql, Map<Integer, ParameterCommand> parameterCommand) {
        SqlType sqlType = SqlTypeUtil.getSqlType(sql);
        SqlParser parser = SqlParserFactory.getParser(sqlType);

        if (parser == null) {

        }

        ParseResult result = new ParseResult();

        parser.init(sql, buildParameters(parameterCommand));

        parser.parse(result);

        ExecutePlan plan = route(result.getTables(), result.getCalculateUnits(), sql, sqlType);

        parser.changeSql(result, plan);

        return plan;
    }

    private ExecutePlan route(void tables, void calculateUnits, String sql, SqlType sqlType) {
    }

    private List<Object> buildParameters(Map<Integer, ParameterCommand> parameterCommand) {
    }
}
