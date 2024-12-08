package org.example;
import java.awt.Desktop;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.net.URI;
import java.util.EnumSet;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainFile {
    public static void main(String[] args) {
        try {
            Server server = new Server(9080);
            ServletContextHandler contextHandler= new ServletContextHandler();
            TemplateEngine engine = new TemplateEngine("tpl");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "12345";
            DatabaseStructure.migrate(url, user, password);
            MemoryService service = new MemoryService(url, user, password);
            contextHandler.addServlet(new ServletHolder(new RegistrationServlet(service,engine)), "/registration");
            contextHandler.addServlet(new ServletHolder(new AuthorizeServlet(service,new RedirectServlet("/home"),engine)), "/login");
            contextHandler.addServlet(new ServletHolder(new HomeServlet(engine)),"/home");
            contextHandler.addServlet(new ServletHolder(new UsersServlet(engine,service)),"/users");
            LikedChoicesServlet liked = new LikedChoicesServlet(engine, service,new RedirectServlet("/exit"));
            contextHandler.addServlet(new ServletHolder(new MessageServlet(engine, service)),"/messages");
            contextHandler.addServlet(new ServletHolder(liked),"/liked");
            contextHandler.addServlet(new ServletHolder(new ExitServlet(engine)),"/exit");
            contextHandler.addServlet(new ServletHolder(new ChoiceServlet(engine,service,new RedirectServlet("/users"),liked)),"/answer/*");
            contextHandler.addFilter(AuthorizationFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
            contextHandler.addFilter(AuthorizationFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));
            contextHandler.addFilter(AuthorizationFilter.class, "/messages", EnumSet.of(DispatcherType.REQUEST));
            contextHandler.addFilter(AuthorizationFilter.class, "/home", EnumSet.of(DispatcherType.REQUEST));
            contextHandler.addFilter(AuthorizationFilter.class, "/answer/*", EnumSet.of(DispatcherType.REQUEST));
            contextHandler.addFilter(AuthorizationFilter.class, "/exit", EnumSet.of(DispatcherType.REQUEST));
            server.setHandler(contextHandler);
            server.start();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:9080/login"));
            } else {
                System.out.println("Desktop API не підтримується. Відкрийте вручну: http://localhost:9080/login");
            }
            server.join();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
