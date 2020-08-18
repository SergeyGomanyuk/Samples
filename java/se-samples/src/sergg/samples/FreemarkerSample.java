package sergg.samples;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:sergeygomanyuk@yandex.ru">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class FreemarkerSample {

    public static final String TEMPLATE =
            "User Detail:\n" +
            "------------\n" +
            "\n" +
            "The following users have exceeded ${userThreshold} of their data allotment for this month:\n" +
            "<#list userIds?split(\",\") as userId>\n" +
            "  - ${.vars[userId+\".number\"]}<#if .vars[userId+\".new\"]??> (New)</#if> has used ${.vars[userId+\".usage\"]} of their ${userAllotment} allotment\n" +
            "</#list>\n" +
            "\n" +
            "The following Users have exceeded $500 in overage charges:\n" +
            "  - \n" +
            "\n" +
            "Account detail:\n" +
            "---------------\n" +
            "\n" +
            "Your overall account has exceeded 95% of your data allotment this month.\n" +
            "\n" +
            "The account has exceeded $1,000 in overage charges.\n" +
            "\n" +
            "There is 1 more day(s) remaining in this billing cycle." +
            "To view your data usage and/or to make any changes please visit..."            ;

    public static void main(String[] args) {
        Map<String, String> context = new HashMap<>();
        context.put("userThreshold", "75%");
        context.put("userAllotment", "5.0G");
        context.put("userIds", "1,2,3");
        context.put("1.number", "416-555-1111");
        context.put("1.usage", "4.2GB");
        context.put("2.number", "416-555-3333");
        context.put("2.usage", "6.3GB");
        context.put("3.number", "416-555-5555");
        context.put("3.usage", "3.8GB");
        context.put("3.new", "yes");

        String content = getContent(TEMPLATE, context);
        System.out.println(content);
    }

    private static ThreadLocal<Configuration> freemarkerConfigurationThreadLocal = new ThreadLocal<Configuration>() {
        @Override
        protected Configuration initialValue() {
            Configuration cfg = new Configuration(new Version(2, 3, 24));
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            cfg.setTemplateLoader(stringTemplateLoader);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            return cfg;
        }
    };

    public static String getContent(String content, Map<String, String> context) {
        try {
            final Configuration freemarkerConfiguration = freemarkerConfigurationThreadLocal.get();
            ((StringTemplateLoader) freemarkerConfiguration.getTemplateLoader()).putTemplate("cm", content);
            final Template template = freemarkerConfiguration.getTemplate("cm");
            final StringWriter stringWriter = new StringWriter();
            template.process(context, stringWriter);
            return stringWriter.toString();
        } catch (Throwable throwable) {
            throw new RuntimeException("Unable to process message template", throwable);
        }
    }
}
