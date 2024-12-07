package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.Cookie;

public class ChoiceServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    private  final MemoryService memoryService;
    private RedirectServlet redirectServlet;
    private final String cookieName = "userId";
    public ChoiceServlet(TemplateEngine templateEngine, MemoryService memoryService, RedirectServlet redirectServlet,LikedChoicesServlet likedChoicesServlet) {
        this.templateEngine = templateEngine;
        this.memoryService = memoryService;
        this.redirectServlet = redirectServlet;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            HashMap<String, Object> model = new HashMap<>();
            Optional<Cookie> userID = Arrays.stream(req.getCookies()).filter(someCookie->someCookie.getName().equals(cookieName)).findFirst();
            if(userID.isPresent()) {
                Integer responderID=Integer.parseInt(userID.get().getValue());
                model.put("answers",memoryService.getChoicesWithAnswer("null",false,responderID));
                templateEngine.render("history.ftl",model,resp);
            }else{
                this.redirectServlet = new RedirectServlet("/exit");
                redirectServlet.doGet(req,resp);
            }
        } catch (Exception e) {
            HashMap<String,Object> data=new HashMap<>(Map.of("errorMessage","Something went wrong,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String uri = req.getRequestURI();
            int userId = Arrays.stream(req.getCookies())
                    .filter(someCookie -> "userId".equals(someCookie.getName())) // Фільтруємо кукі за назвою "userId"
                    .map(Cookie::getValue) // Отримуємо значення кукі
                    .findFirst() // Беремо перший знайдений результат
                    .map(Integer::parseInt) // Конвертуємо значення у число
                    .orElseThrow(() -> new IllegalArgumentException("userId cookie not found or invalid"));
            User responder = memoryService.getById(userId);
            if (uri.equals("/answer/yes")) {
                Choice likedChoice = new Choice("yes",responder,new User(Integer.parseInt(req.getParameter("id")),req.getParameter("name"),Integer.parseInt(req.getParameter("age")),req.getParameter("file"),req.getParameter("password"),req.getParameter("login"),req.getParameter("gender")));
                memoryService.saveChoice(likedChoice);
            }
            if (uri.equals("/answer/no")) {
                memoryService.saveChoice(new Choice("no",responder,new User(Integer.parseInt(req.getParameter("id")),req.getParameter("name"),Integer.parseInt(req.getParameter("age")),req.getParameter("file"),req.getParameter("password"),req.getParameter("login"),req.getParameter("gender"))));
            }
            redirectServlet.doPost(req, resp);
        }catch (Exception e) {
            HashMap<String,Object> data=new HashMap<>(Map.of("errorMessage","Something went wrong,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }

    }

}
