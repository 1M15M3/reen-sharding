package com.aude.sharding.jdbc;

import com.aude.sharding.jdbc.merge.MergeColumn;
import com.aude.sharding.jdbc.merge.groupby.GroupbyResultSet;
import com.aude.sharding.router.model.ExecutePlan;
import com.aude.sharding.jdbc.merge.agg.AggResultSet;
import com.aude.sharding.jdbc.merge.iterator.IteratorResutSet;
import com.aude.sharding.jdbc.merge.orderby.OrderByResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/1/29.
 *
 * 结果集合并入口
 */
public class TMerger {

    public static TResultSet mearge(ExecutePlan plan, List<ResultSet> sets, TStatement outStmt) throws SQLException {

        if (sets == null || sets.size() <= 1){
            return new IteratorResutSet(sets, outStmt, plan);
        }

        // groupby
        // groupby + orderby
        // agg + groupby
        // agg + groupby + orderby
        List<String> groupColumns = plan.getGroupbyColumns();
        if (groupColumns != null && groupColumns.size() > 0){
            return new GroupbyResultSet(sets, outStmt, plan);
        }

        // agg
        // agg+orderby 没有这种情况 因为只有一行数据
        Map<String, MergeColumn.MergeType> mergeColumns = plan.getMergeColumns();
        if (mergeColumns != null && mergeColumns.size() > 0){
            return new AggResultSet(sets, outStmt, plan);
        }

        // orderby
        if (plan.getOrderbyColumns() != null && plan.getOrderbyColumns().size() > 0){
            return new OrderByResultSet(sets, outStmt, plan);
        }

        // 普通ResultSet
        return new IteratorResutSet(sets, outStmt, plan);
    }

}
