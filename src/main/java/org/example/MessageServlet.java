package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class MessageServlet extends HttpServlet {
    TemplateEngine templateEngine;;
    MemoryService memoryService;
    RedirectServlet redirectServlet;
    public MessageServlet(TemplateEngine engine,MemoryService memoryService) {
        this.templateEngine = engine;
        this.memoryService = memoryService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = memoryService.getById(Integer.parseInt(req.getParameter("id")));
        HashMap<String,Object> data = new HashMap<>();
        if(user != null) {
            // Отримуємо cookies
            Cookie[] cookies = req.getCookies();
            Integer fromWho = null;

            // Знаходимо cookie з ім'ям "fromWho"
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userId".equals(cookie.getName())) {
                        fromWho = Integer.parseInt(cookie.getValue());
                        break;
                    }
                }
            }

            // Перевірка: якщо fromWho відсутній, перенаправляємо на сторінку авторизації
            if (fromWho == null) {
                resp.sendRedirect("/authorize");
                return;
            }
            if(user.getId()==fromWho) {
               data.put("message","You can not send message to yourself");
               templateEngine.render("home.ftl",data,resp);
            }
            data.put("user", user);
            // Отримуємо всі повідомлення з бази даних
            List<Message> chatHistory = memoryService.getAll(user.getId(), fromWho);
            List<String> chatHistoryString = new ArrayList<>();
            chatHistory.forEach(mess->chatHistoryString.add(mess.sendMessage(memoryService.getById(mess.getFrom()).getName())));
            data.put("message",null);
            data.put("chatHistory", chatHistoryString);
            templateEngine.render("chat.ftl",data,resp);
        }
        else {
            data.put("message","There is no user with this id");
            templateEngine.render("home.ftl",data,resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Отримуємо cookies
        Cookie[] cookies = req.getCookies();
        Integer fromWho = null;

        // Знаходимо cookie з ім'ям "fromWho"
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    fromWho = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }

        // Перевірка: якщо fromWho відсутній, перенаправляємо на сторінку авторизації
        if (fromWho == null) {
            resp.sendRedirect("/authorize");
            return;
        }

        // Отримуємо повідомлення від користувача
        String userMessage = req.getParameter("message");

        // Отримуємо дані про користувача

        Integer toWho = Integer.parseInt(req.getParameter("id"));
        // Зберігаємо повідомлення в базі даних
        Message message = new Message(// id генерується автоматично
                null,
                userMessage, // текст повідомлення
                LocalDateTime.now(), // час повідомлення
                fromWho,// від кого (UserІd)
                toWho// до кого (id аресата)
        );
        memoryService.save(message);
        this.redirectServlet = new RedirectServlet(String.format("messages?id=%d",toWho));
        redirectServlet.doGet(req, resp);
    }

}
