package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AuthorizeServlet extends HttpServlet {
     private  final MemoryService memoryService;
     private   final RedirectServlet redirectServlet;
     private final TemplateEngine templateEngine;
     public AuthorizeServlet(MemoryService memoryService, RedirectServlet redirectServlet, TemplateEngine templateEngine) {
         this.memoryService = memoryService;
         this.redirectServlet = redirectServlet;
         this.templateEngine = templateEngine;
     }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String password=req.getParameter("password");
            String login = req.getParameter("login");
            // Якщо параметри відсутні, просто відображаємо сторінку
            if (Objects.equals(login, null) && Objects.equals(password, null)) {
                HashMap<String, Object> data = new HashMap<>();
                templateEngine.render("authorize.ftl", data, resp);
            }
            Optional<User> optionalUser = memoryService.getAllUsers().stream()
                    .filter(user -> user.getPassword().equals(password) && user.getLogin().equals(login))  // фільтруємо за паролем
                    .findFirst();
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Створюємо Cookies для збереження логіна, пароля та id
                Cookie loginCookie = new Cookie("login", login);
                loginCookie.setMaxAge(60 * 60 * 8);
                Cookie passwordCookie = new Cookie("password", password);
                passwordCookie.setMaxAge(60 * 60 * 8);
                Cookie idCookie = new Cookie("userId", String.valueOf(user.getId()));
                idCookie.setMaxAge(60 * 60 * 8);

                // Додаємо Cookies до відповіді
                resp.addCookie(loginCookie);
                resp.addCookie(passwordCookie);
                resp.addCookie(idCookie);

                // Редирект до іншого сервлета
                redirectServlet.doGet(req, resp);
            } else {
                // Відображення повідомлення про помилку авторизації
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "There isn't the user with such login and password");
                templateEngine.render("authorize.ftl", data, resp);
            }
        }catch (Exception e){
            HashMap<String,Object> data=new HashMap<>(Map.of("errorMessage","Something went wrong,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }

    }

}
