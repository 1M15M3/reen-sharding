package com.aude.sharding.parser.mysql;

import com.aude.sharding.test.PrintUtil;
import com.aude.sharding.parser.SqlParser;
import com.aude.sharding.parser.SqlParserFactory;
import com.aude.sharding.parser.model.ParseResult;
import com.aude.sharding.parser.model.SqlType;
import org.junit.Test;

/**
 * Created by sidawei on 16/3/21.
 */
public class SimpleParserTest {

    SqlParser selectParser = SqlParserFactory.getParser(SqlType.SELECT);

    @Test
    public void test_0(){

    }

    private void test(String sql){
        ParseResult result = new ParseResult();
        selectParser.init(sql, null);
        selectParser.parse(result);
        PrintUtil.printCalculates(result.getCalculateUnits());
    }

}
