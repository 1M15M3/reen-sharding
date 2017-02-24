package com.aude.sharding.router;

import com.aude.sharding.jdbc.model.ParameterCommand;
import com.aude.sharding.router.model.ExecutePlan;

import java.util.Map;

/**
 * SQL路由服务入口
 * @author dawei
 *
 */
public interface IRouteService {

    /**
     * 路由入口
     * @param sql   原始的sql语句
     * @param parameterCommand 业务层通过setString(1, "")等设置的参数
     * @return
     */
	ExecutePlan doRoute(String sql, Map<Integer, ParameterCommand> parameterCommand);

}
