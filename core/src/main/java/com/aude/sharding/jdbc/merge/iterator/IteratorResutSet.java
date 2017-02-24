package com.aude.sharding.jdbc.merge.iterator;

import com.aude.sharding.router.model.ExecutePlan;
import com.aude.sharding.jdbc.TStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sidawei on 16/1/31.
 *
 * Forward_Only
 */
public class IteratorResutSet extends IteratorResultSetGetterAdapter {

    protected int       index = 0;
    protected boolean   end;

    public IteratorResutSet(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) {
        super(listResultSet, statement, plan);
    }

    @Override
    public boolean nextRow() throws SQLException {
        if (getResultSet() == null || end) {
            return false;
        }
        if (index == getResultSet().size()) {
            return false;
        }
        currentResultSet = getResultSet().get(index);
        boolean next = currentResultSet.next();
        if (next) {
            return true;
        }else{
            index++;
        }
        return nextRow();
    }

    /**
     * TODO 暂时以第一个结果集的列为准
     *
     * @return
     */
    public boolean needEscape() {
        if (index > 0) {
            return true;
        } else {
            return false;
        }
    }
}
