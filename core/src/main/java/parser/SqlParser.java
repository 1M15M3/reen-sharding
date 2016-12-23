package parser;

import parser.model.ParseResult;
import router.model.ExecutePlan;

import java.util.List;

/**
 * Created by aude on 2016/12/23.
 */
public interface SqlParser {
    void parse(ParseResult result);

    void changeSql(ParseResult result, ExecutePlan plan);


    void init(String sql, List<Object> objects);
}
