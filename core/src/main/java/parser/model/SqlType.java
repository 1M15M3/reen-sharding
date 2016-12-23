package parser.model;

/**
 * sql语句的类型
 * Created by aude on 2016/12/23.
 */
public enum SqlType {
    SELECT(0),
    INSERT(1),
    UPDATE(2),
    DELETE(3),
    SELECT_FOR_UPDATE(4),
    REPLACE(5),
    EXPLAIN(6),
    OTHER(-1);

    private int i;

    private SqlType(int i) {
        this.i = i;
    }

    public int value() {
        return this.i;
    }

    public static SqlType valueOf(int i) {
        for (SqlType t : values()) {
            if (t.value() == i) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid SqlType:" + i);
    }
}
