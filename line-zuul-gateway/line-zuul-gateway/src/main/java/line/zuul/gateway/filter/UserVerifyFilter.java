package line.zuul.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import line.zuul.gateway.service.JWTVerifyService;
import lombok.extern.slf4j.Slf4j;

/**
 * http://www.cnblogs.com/ityouknow/p/8391593.html
 * 
 * 检查http请求头部中的jwt是否正确
 * 
 * @author line
 */
@Slf4j
public class UserVerifyFilter extends ZuulFilter {
	
	@Autowired
	private JWTVerifyService verifyService;

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String IP = request.getHeader("X-Forwarded-For");
		log.info("request from: {}", IP);
		String jwt = request.getHeader("token");
		if (!verifyService.verify(jwt)) {
			ctx.getResponse().setStatus(408);
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody("Failed token verification!");
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
