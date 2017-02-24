package com.aude.sharding.parser.mysql;

import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.aude.sharding.exception.shardingException;
import com.aude.sharding.parser.model.ParseResult;
import com.aude.sharding.router.model.ExecutePlan;
import com.aude.sharding.router.model.ExecuteType;
import com.aude.sharding.support.shardingContext;
import com.aude.sharding.utils.StringUtil;

import java.util.List;

/**
 * Created by sidawei on 16/1/15.
 *
 * update解析
 */
public class MySqlUpdateParser extends MySqlSqlParser {

    @Override
    public void changeSql(ParseResult result, ExecutePlan plan) {
        if (plan.getExecuteType() != ExecuteType.NO){
            checkUpdateColumn();
        }
        super.changeSql(result, plan);
    }

    /**
     * 判断某个列是否能被update,分区表的分区列是不能被update的.
     */
    protected void checkUpdateColumn(){
        MySqlUpdateStatement update = (MySqlUpdateStatement)statement;
        String tableName = StringUtil.removeBackquote(update.getTableName().getSimpleName());
        List<SQLUpdateSetItem> updateSetItem = update.getItems();
        String[] partitionColumns = shardingContext.getPartitionColumns(tableName);
        if(partitionColumns != null && partitionColumns.length > 0 && updateSetItem != null && updateSetItem.size() > 0) {
            for(SQLUpdateSetItem item : updateSetItem) {
                String column = StringUtil.removeBackquote(item.getColumn().toString());
                if (StringUtil.contains(partitionColumns, column)){
                    throw new shardingException("分区表的分区键不能被更新:" + tableName + "." + column);
                }
            }
        }
    }
}
