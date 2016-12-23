package parser.utils;

import parser.model.SqlType;

/**
 * Created by aude on 2016/12/23.
 */
public class SqlTypeUtil {

    /**
     * 快速判断sql的类型
     *
     * @param sql
     * @return
     */
    public static SqlType getSqlType(String sql) {
        if (sql == null || sql.length() == 0) {
            return null;
        }

        while (true) {
            if (sql.startsWith("(")) {
                sql.substring(1).trim();
            } else {
                break;
            }
        }

        sql = sql.trim();
        sql = sql.toLowerCase();
        SqlType sqlType = SqlType.OTHER;
        if (sql.startsWith("select")) {
            sqlType = SqlType.SELECT;
        } else if (sql.startsWith("update")) {
            sqlType = SqlType.UPDATE;
        } else if (sql.startsWith("insert")) {
            sqlType = SqlType.INSERT;
        } else if (sql.startsWith("delete")) {
            sqlType = SqlType.DELETE;
        } else if (sql.startsWith("explain")) {
            sqlType = SqlType.EXPLAIN;
        }
        return sqlType;
    }


}
