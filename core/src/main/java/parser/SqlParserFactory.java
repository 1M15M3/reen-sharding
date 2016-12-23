package parser;

import parser.model.SqlType;
import parser.mysql.*;

/**
 * sql解析器工厂,根据不同的sql类型返回不同的解析器
 * <p>
 * Created by aude on 2016/12/23.
 */
public class SqlParserFactory {
    public static SqlParser getParser(SqlType sqlType) {
        if (sqlType == null) {
            return null;
        }
        switch (sqlType) {
            case SELECT:
                return new MySqlSelectParser();
            case DELETE:
                return new MySqlDeleteParser();
            case EXPLAIN:
                return new MySqlExplainParser();
            case INSERT:
                return new MySqlInsertParser();
            case UPDATE:
                return new MySqlUpdateParser();
            default:
                return null;

        }
    }
}
