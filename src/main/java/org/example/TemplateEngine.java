package org.example;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;

public class TemplateEngine {
    private final Configuration configuration;
    public TemplateEngine(String rootFolder) {
        configuration = new Configuration(Configuration.VERSION_2_3_33);
        try {
            InputStream templateStream = getClass().getClassLoader().getResourceAsStream(rootFolder);
            if (templateStream == null) {
                throw new RuntimeException("Шлях до шаблонів не знайдений: " + rootFolder);
            }

            configuration.setClassForTemplateLoading(getClass(), "/" + rootFolder);

        } catch (RuntimeException e) {
            throw new RuntimeException("Помилка ініціалізації шаблонів: " + e.getMessage(), e);
        }
    }

    public void render(String templateName, Map<String, Object> data, HttpServletResponse res) {
        try (Writer writer = res.getWriter()) {
            configuration.getTemplate(templateName).process(data, writer);
        } catch (TemplateException | java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
