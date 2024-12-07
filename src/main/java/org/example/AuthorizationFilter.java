package org.example;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    private boolean isHttp(ServletRequest request,ServletResponse response) {
         return request instanceof HttpServletRequest && response instanceof HttpServletResponse;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
          if(!isHttp(servletRequest,servletResponse)){
              filterChain.doFilter(servletRequest,servletResponse);
          }else{
               HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
               HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
              Optional<List<Cookie>> authCookies =CookieCheck.getAuthCookies(httpRequest);
               if(authCookies.isPresent() && authCookies.get().size() == 3){

                 filterChain.doFilter(servletRequest,servletResponse);
               }else {
                   httpResponse.sendRedirect("/login");
               }
          }
    }

    @Override
    public void destroy() {

    }
}
