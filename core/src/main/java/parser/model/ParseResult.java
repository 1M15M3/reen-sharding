package parser.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * sql解析结果
 * Created by aude on 2016/12/23.
 */
public class ParseResult {

    /**
     * 路由计算单元
     */
    private List<CalculateUnit> calculateUnits;

    /**
     * 原始sql
     */
    private String sql = "";

    /**
     * sql中的所有的表名
     * tables为路由计算共有属性,多组RouteCalculateUnit使用相同的tables
     */
    private List<String> tables = new ArrayList<String>();

    /**
     * sql中所有的表名
     * key table alias
     * value table realname
     */
    private Map<String, String> tableAliasMap = new LinkedHashMap<String, String>();

    public void getTables() {
    }

    public void getCalculateUnits() {
    }
}
