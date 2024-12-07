package org.example;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

public class DatabaseStructure {
    public static void migrate(String url,String user,String password) {
     var conf = new FluentConfiguration().dataSource(url,user,password);
     var flyway = new Flyway(conf);
     flyway.migrate();
    }
}
