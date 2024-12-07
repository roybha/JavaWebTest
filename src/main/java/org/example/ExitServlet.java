package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ExitServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    public ExitServlet(TemplateEngine templateEngine) {
      this.templateEngine=templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            templateEngine.render("authorize.ftl",new HashMap<>(),resp);
        } catch (Exception e) {
            HashMap<String,Object> data=new HashMap<>(Map.of("errorMessage","Something went wrong,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            List<Cookie> authorizeCookies = new ArrayList<>();
            CookieCheck.cookies.forEach(cookie -> authorizeCookies.add(new Cookie(cookie,"")));
            authorizeCookies.forEach(x->{x.setMaxAge(0);resp.addCookie(x);});
            templateEngine.render("authorize.ftl",new HashMap<>(),resp);
        }catch(Exception e){
            HashMap<String,Object> data=new HashMap<>(Map.of("errorMessage","Something went wrong,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }

    }
}
