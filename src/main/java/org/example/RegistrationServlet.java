package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    private final MemoryService memoryService;
    public RegistrationServlet(MemoryService memoryService, TemplateEngine templateEngine) {
        this.memoryService = memoryService;
        this.templateEngine = templateEngine;
    }
    private boolean isValidUrl(String fileUrl) {
        try {
            if (!fileUrl.matches("^https?://.*\\.(jpg|jpeg|png|gif|webp)$")) {
                return false;
            }
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            String contentType = connection.getContentType();
            int responseCode = connection.getResponseCode();

            return responseCode == 200 && contentType.startsWith("image/");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HashMap<String,Object> data = new HashMap<>();
            templateEngine.render("registrationPage.ftl",data,resp);
        }catch (Exception e) {
            HashMap<String,Object> data = new HashMap<>(Map.of("errorMessage","Something went in that way that we don't expected,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HashMap<String, Object> data = new HashMap<>();
            String login = req.getParameter("login");
            boolean userExists = memoryService.getAllUsers().stream()
                    .anyMatch(someUser -> someUser.getLogin().equals(login));
            String password = req.getParameter("password");
            if(password.length()<8){
                data.put("message","Password must have length at least 8 characters");
            }else if (login.length()<8){
                data.put("message","Login must have length at least 8 characters");
            }else if(userExists){
                data.put("message","User with such login is already exists");
            }else if(!req.getParameter("confirm-password").equals(password)){
                data.put("message","Password does not match");
            }else if(!isValidUrl(req.getParameter("file"))){
                data.put("message","Invalid file-URL");
            }

            if(data.containsKey("message")){
                templateEngine.render("registrationPage.ftl",data,resp);
            }else{
                String name = req.getParameter("name");
                int age  = Integer.parseInt(req.getParameter("age"));
                String file = req.getParameter("file");
                String gender = req.getParameter("gender");
                memoryService.saveUser(new User(memoryService.getMemory().getUserInterface().getMaxID(),name,age,file,password,login,gender));
                templateEngine.render("authorize.ftl",new HashMap<>(),resp);
            }
        } catch (Exception e) {
            HashMap<String,Object> data = new HashMap<>(Map.of("errorMessage","Something went in that way that we don't expected,sorry((("));
            templateEngine.render("errorPage.ftl",data,resp);
        }

    }
}
