package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class HomeServlet extends HttpServlet {
    private  final TemplateEngine templateEngine;
    public HomeServlet(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String,Object> data = new HashMap<>();
        try{
           templateEngine.render("home.ftl",data,resp);
       }catch(Exception e){
           data.put("errorMessage","Something went wrong,sorry(((");
           templateEngine.render("errorPage.ftl",data,resp);
       }

    }
}
