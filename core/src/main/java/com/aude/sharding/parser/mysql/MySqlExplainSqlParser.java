package com.aude.sharding.parser.mysql;

import java.util.List;

import com.aude.sharding.exception.shardingException;
import com.aude.sharding.parser.SqlParser;
import com.aude.sharding.parser.SqlParserFactory;
import com.aude.sharding.parser.model.ParseResult;
import com.aude.sharding.parser.model.SqlType;
import com.aude.sharding.router.model.ExecutePlan;
import com.aude.sharding.parser.utils.SqlTypeUtil;

/**
 * explan命令解析器
 */
public class MySqlExplainSqlParser  implements SqlParser {
	
	private SqlParser sqlParser;
	
	@Override
	public void init(String sql, List<Object> parameters) {
		String newSQL = sql.substring(7).trim();

		SqlType sqlType = SqlTypeUtil.getSqlType(newSQL);

		sqlParser = SqlParserFactory.getParser(sqlType);

		if (sqlParser == null) {
			 throw new shardingException("Explain未找到解析器");
		}
		sqlParser.init(newSQL, parameters);
	}

	@Override
	public void parse(ParseResult result) {
		sqlParser.parse(result);
	}

	@Override
	public void changeSql(ParseResult result, ExecutePlan plan) {
		sqlParser.changeSql(result, plan);
	}

}
