package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LikedChoicesServlet extends HttpServlet {
     private final MemoryService memoryService;
     TemplateEngine templateEngine;
     private final String cookieName = "userId";
     private final RedirectServlet redirectServlet;
    public LikedChoicesServlet(TemplateEngine templateEngine, MemoryService memoryService, RedirectServlet redirectServlet) {
        this.templateEngine = templateEngine;
        this.memoryService = memoryService;
        this.redirectServlet = redirectServlet;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            HashMap<String,Object> data = new HashMap<>();
            Optional<Cookie> userID = Arrays.stream(req.getCookies()).filter(someCookie->someCookie.getName().equals(cookieName)).findFirst();
            if(userID.isPresent()) {
                Integer responderID = Integer.parseInt(userID.get().getValue());
                data.put("answers",memoryService.getChoicesWithAnswer("yes",true,responderID));
                templateEngine.render("history.ftl",data,resp);
            }else{
                redirectServlet.doGet(req,resp);
            }
        }catch(Exception e) {
            HashMap<String,Object> data = new HashMap<>(Map.of("errorMessage", "Something went wrong,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }


    }
}
