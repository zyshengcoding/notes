package com.ibm.util;

import com.ibm.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
 /*       Cookie[] cookies = request.getCookies();
      for (int i =0;i<cookies.length;i++){
          System.out.println(cookies[i].getName()+" cookieName ");
          System.out.println(cookies[i].getValue()+" cookieValue ");
          System.out.println(cookies[i].getVersion()+" cookieVersion ");
      }*/
        System.out.println("requestURI{} " + requestURI + " session{} " + session);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
