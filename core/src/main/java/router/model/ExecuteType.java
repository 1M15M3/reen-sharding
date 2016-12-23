package router.model;

/**
 * Created by aude on 2016/12/23.
 */
public enum ExecuteType {

    NO(0, "无需路由"),
    PARTTION(1, "分区执行"),
    ALL(2, "全表扫描");

    private int key;

    private String value;

    ExecuteType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExecuteType{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
