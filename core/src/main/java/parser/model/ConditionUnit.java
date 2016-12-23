package parser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * WHERE语句中的条件 代表 column = value
 * Created by aude on 2016/12/23.
 */
public class ConditionUnit {
    /**
     * 表名
     */
    private String table;

    /**
     * 列名
     */
    private String column;

    /**
     * 字段的值,因为操作符可能是in,所以可能会有多个
     */
    private List<String> values;

    /**
     * 条件操作符
     */
    private ConditionUnitOperator operator;

    public ConditionUnit() {
    }

    public static ConditionUnit buildConditionUnit(String table0, String column0, Object[] values0, ConditionUnitOperator operator0) {
        if (table0 == null || table0.length() == 0) {
            return null;
        }
        ConditionUnit unit = new ConditionUnit();
        unit.table = table0;
        unit.column = column0;
        unit.operator = operator0;
        unit.values = new ArrayList<>(values0.length);
        for (Object value : values0) {
            if (value != null) {
                unit.values.add(value.toString());
            }
        }
        if (values0.length == 0) {
            return null;
        }
        return unit;
    }

    @Override
    public int hashCode() {
        return column.hashCode() + values.hashCode() + operator.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        ConditionUnit o = (ConditionUnit) obj;
        return table.equals(o.getTable())
        && column.equals(o.getColumn())
                && values.equals(o.getValues())
                && operator.equals(o.operator);
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public ConditionUnitOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionUnitOperator operator) {
        this.operator = operator;
    }
}
