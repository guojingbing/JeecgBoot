package org.jeecg.config.shiro.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.config.shiro.DefContants;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 小程序鉴权登录拦截器
 * @Author: Scott
 * @Date: 2018/10/7
 **/
@Slf4j
public class APITokenFilter extends BasicHttpAuthenticationFilter {
	/**
	 * 执行登录认证
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			return executeLogin(request, response);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		String authzHeader = this.getAuthzHeader(request);
		return authzHeader != null && this.isLoginAttempt(authzHeader);
	}

	/**
	 *
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
		ServletContext context = request.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		RedisUtil redisUtil = ctx.getBean(RedisUtil.class);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader(DefContants.X_ACCESS_TOKEN);
		if(StringUtils.isEmpty(token)){
			return false;
//			throw new AuthenticationException("您未登录，请先登录");
		}
		if(JwtUtil.verfiyMpApiTokenExp(token,redisUtil)){
			return false;
//			throw new AuthenticationException("登录超时，请重新登录");
		}
		String userid = JwtUtil.getUsername(token);
		if(StringUtils.isEmpty(userid)){
			return false;
//			throw new AuthenticationException("登录失效，请重新登录");
		}
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean loggedIn = false;
		if (this.isLoginAttempt(request, response)) {
			loggedIn = this.executeLogin(request, response);
		}

		if (!loggedIn) {
			this.sendChallenge(request, response);
		}

		return loggedIn;
	}

	@Override
	protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		httpResponse.setStatus(401);
		response.setContentType("application/json;charset=utf-8");
		try {
			JSONObject reObj=new JSONObject();
			reObj.put("message","登录失效，请先登录");
			response.getWriter().write(JSON.toJSONString(reObj));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}
}
