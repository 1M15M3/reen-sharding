package router;

import jdbc.model.ParameterCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.SqlParser;
import parser.SqlParserFactory;
import parser.model.CalculateUnit;
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

    /**
     * 根据CalculateUnit进行路由计算
     *
     * @param tables         sql中提取到的所有表名
     * @param calculateUnits sql中提取到的计算单元
     * @param sql            原始sql
     * @param sqlType        sql类型
     * @return
     */
    private ExecutePlan route(List<String> tables, List<CalculateUnit> calculateUnits, String sql, SqlType sqlType) {
    }

    private List<Object> buildParameters(Map<Integer, ParameterCommand> parameterCommand) {
    }
}
