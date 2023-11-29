/* package domain.models.entities;

public class WebApp {

    public static void main(String[] args) {
        initTemplateEngine();
        RepoProducto repo = new RepoProducto();
        Integer port = Integer.parseInt(System.getProperty("port", "8080"));
        Javalin app = Javalin.create(config()).start(port);
        app.get("/", ctx -> ctx.result("Hola Mundo"));
        app.post("/api/productos", new AltaProductoController(repo));
        app.get("/api/productos", new ListaProductoController(repo));
        app.delete("/api/productos/{prodId}", new BajaProductoController(repo));
        app.get("/productos/{prodId}", new UIProductosController(repo));
        app.get("/productos", new UIListProductosController(repo));
        app.post("/productos", new UIAltaProductosController(repo));
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });
        };
    }

    private static void initTemplateEngine() {
        JavalinRenderer.register((path, model, context) -> {
                    // Funcion que renderiza el template
                    Handlebars handlebars = new Handlebars();
                    Template template = null;
                    try {
                        template = handlebars.compile("templates/" + path.replace(".hbs", ""));
                        return template.apply(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la pagina indicada...";
                    }
                }, ".hbs"
        );
    }
} */
