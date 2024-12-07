package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {
    TemplateEngine templateEngine;
    private final MemoryService memoryService;
    private boolean isMale = true;
    private Integer lastSelectedMaleId = null;  // Зберігаємо останній вибраний ID чоловіка
    private Integer lastSelectedFemaleId = null;  // Зберігаємо останній вибраний ID жінки

    public UsersServlet(TemplateEngine templateEngine,MemoryService memoryService) {
        this.templateEngine = templateEngine;
        this.memoryService = memoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            HashMap<String,Object> context = generateSomeone();
            templateEngine.render("usersPage.ftl",context,resp);
        } catch (RuntimeException e) {
            HashMap<String,Object> context = new HashMap<>(Map.of("errorMessage",e.getMessage()));
            templateEngine.render("errorPage.ftl",context,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HashMap<String,Object> context = generateSomeone();
            templateEngine.render("usersPage.ftl",context,resp);
        } catch (RuntimeException e) {
            HashMap<String,Object> context = new HashMap<>(Map.of("errorMessage",e.getMessage()));
            templateEngine.render("errorPage.ftl",context,resp);
        }
    }
    private HashMap<String, Object> generateSomeone() {
        HashMap<String, Object> context = new HashMap<>();
        List<User> maleUsers = memoryService.getUsersByGender("man");
        List<User> femaleUsers = memoryService.getUsersByGender("woman");

        if (maleUsers.isEmpty() && femaleUsers.isEmpty()) {
            throw new RuntimeException("No users found in the database.");
        }

        User selectedUser = null;

        // Генерація для чоловіків
        if (isMale) {
            selectedUser = getNextUserWithHigherId(maleUsers, lastSelectedMaleId);
            lastSelectedMaleId = selectedUser.getId();  // Оновлюємо останній вибраний ID
        } else {
            // Генерація для жінок
            selectedUser = getNextUserWithHigherId(femaleUsers, lastSelectedFemaleId);
            lastSelectedFemaleId = selectedUser.getId();  // Оновлюємо останній вибраний ID
        }

        // Заповнюємо контекст для вибраного користувача
        context.put("id", selectedUser.getId());
        context.put("name", selectedUser.getName());
        context.put("age", selectedUser.getAge());
        context.put("file", selectedUser.getFile());
        context.put("gender", selectedUser.getGender());

        // Перемикаємо прапорець на інший пол
        isMale = !isMale;
        return context;
    }

    private User getNextUserWithHigherId(List<User> users, Integer lastSelectedId) {
        // Сортуємо список користувачів за ID
        users.sort(Comparator.comparingLong(User::getId));

        User nextUser = null;
        for (User user : users) {
            // Шукаємо наступного користувача з більшим ID, ніж останній вибраний
            if (lastSelectedId == null || user.getId() > lastSelectedId) {
                nextUser = user;
                break;
            }
        }

        // Якщо не знайдено користувача з більшим ID, повертаємо першого користувача
        if (nextUser == null) {
            nextUser = users.getFirst();
        }

        return nextUser;
    }

}
