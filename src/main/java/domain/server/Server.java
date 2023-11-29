package domain.server;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import domain.models.entities.roles.Permiso;
import domain.server.handlers.AppHandlers;
import domain.server.init.Initializer;
import domain.server.middlewares.AuthMiddleware;
import domain.server.utils.PrettyProperties;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.ValueResolver;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.text.SimpleDateFormat;
import org.hibernate.cfg.Configuration;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if(app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if(app == null) {
            PrettyProperties.getInstance();
            Integer port = Integer.parseInt(System.getProperty("port", "8080"));
            app = Javalin.create(config()).start(port);
            initTemplateEngine();
            AppHandlers.applyHandlers(app);
            Router.init();

            if(Boolean.parseBoolean(PrettyProperties.getInstance().propertyFromName("dev_mode"))) {
                Initializer.init();
            }

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://dpg-cl8jutf6e7vc73a76bq0-a.oregon-postgres.render.com:5432/persistenciatp");
            configuration.setProperty("hibernate.connection.username", "tp");
            configuration.setProperty("hibernate.connection.password", "nr8cZK5SIFz5RJBFpD7NkuckvUgGLSi1");

        }
    }


    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "public";
            });
            AuthMiddleware.apply(config);
        };
    }

    private static void initTemplateEngine() {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("contienePermiso", (List<Permiso> permisos, Options options) -> {
            String permisoBuscado = options.param(0);
            return (permisos != null && permisos.stream().anyMatch(p -> p.coincideConNombreInterno(permisoBuscado))) ?
                    options.fn() :
                    options.inverse();
        });
        handlebars.registerHelper("formatDate", (date, options) -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.format(date);
        });
        handlebars.registerHelper("toJson", (obj, options) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Convertir el objeto a JSON como cadena
                return objectMapper.writeValueAsString(obj);
            } catch (IOException e) {
                throw new RuntimeException("Error converting to JSON", e);
            }
        });
        JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
                    Template template = null;
                    try {
                        template = handlebars.compile(
                                "templates/" + path.replace(".hbs",""));
                        return template.apply(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Extensión del archivo de template
        );
    }
}