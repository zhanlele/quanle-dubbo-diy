package com.quanle.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author quanle
 * @date 2020/6/2 9:27 PM
 */
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER})
public class TransportIPFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String requestIp = RpcContext.getContext().getAttachment(ServiceConstants.IP_KEY);
        if (!StringUtils.isEmpty(requestIp)) {
            IpUtil.setIp(requestIp);
        } else {
            RpcContext.getContext().setAttachment(ServiceConstants.IP_KEY, IpUtil.getIp());
        }
        // 执行方法
        return invoker.invoke(invocation);

    }
}
