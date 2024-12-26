package org.flowerfog.interceptor;

import io.jsonwebtoken.Claims;
import org.flowerfog.entity.ResultCode;
import org.flowerfog.exception.CommonException;
import org.flowerfog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 *      继承
 *
 *      preHandle 进入到控制方法前执行
 *          boolean
 *              true 可以继续执行控制器方法
 *              false 拦截
 *      postHandle 执行控制方法后执行
 *      afterCompletion 响应结束之前执行
 *
 *
 * 1.简化获取token数据的代码
 *  统一用户权限校验（是否登录）
 * 2.判断用户是否具有当前访问接口的权限
 *
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;
    /**
     * 简化获取token数据的代码
     *  统一用户权限校验（是否登录）
     *
     * 1.通过request获取token
     * 2.从token解析claims
     * 3.奖claims绑定request域中
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.通过request获取token
        String authorization = request.getHeader("Authorization");

        //判断请求头是否为空，或者是否为Bearer 开头
        if(!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")){
            //获取token数据
            String token = authorization.replace("Bearer ","");
            //解析token获取claims
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null){

                //通过claims获取当前用户可获取API权限字符串
                String apis = (String) claims.get("apis");//api-user-delete,api-user-update
                //通过handler
                HandlerMethod h = (HandlerMethod) handler;
                //获取接口上requestmapping注解
                RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);
                String name = annotation.name();
                //判断用户是否有响应的权限
                if (apis.contains(name)){
                    request.setAttribute("user_claims",claims);
                    return true;
                }
            }else {
                throw new CommonException(ResultCode.UNAUTHENTICATED);
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }


}
