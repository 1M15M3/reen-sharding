package parser;

import parser.model.ParseResult;
import router.model.ExecutePlan;

import java.util.List;

/**
 * Created by aude on 2016/12/23.
 */
public interface SqlParser {
    /**
     * 初始化解析器
     * @param sql
     * @param objects
     */
    void init(String sql, List<Object> objects);

    /**
     * 解析sql
     * @param result
     */
    void parse(ParseResult result);

    /**
     * sql改写 limit,聚合函数,表名,读写分离等
     * @param result
     * @param plan
     */
    void changeSql(ParseResult result, ExecutePlan plan);
}
