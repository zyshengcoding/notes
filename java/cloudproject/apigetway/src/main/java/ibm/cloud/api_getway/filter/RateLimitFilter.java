package ibm.cloud.api_getway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 结合guava令牌桶算法进行限流拦截
 */
@Component
public class RateLimitFilter extends ZuulFilter {

    //每秒产生1000令牌
    private static final RateLimiter limit = RateLimiter.create(1000);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    //值最小，表示最先进行
    @Override
    public int filterOrder() {
        return -8;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //uri匹配进行拦截
        if ("/api/pt/api/v1/product/find".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        //如果没获取到,直接进行研制，不进行请求
       if (!limit.tryAcquire()){
           currentContext.setSendZuulResponse(false);
           currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
       }

        return null;
    }
}
