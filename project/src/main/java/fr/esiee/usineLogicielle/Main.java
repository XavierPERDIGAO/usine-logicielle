package fr.esiee.usineLogicielle;

import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

/**
 * Entrée du programme
 * 
 * @author perdigao
 *
 */
public class Main implements SparkApplication 
{
    private HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

    /**
     * juste pour le test.
     * @param args
     */
    public static void main(String[] args)
    {
        SparkApplication application = new Main();
        application.init();
    }

    /**
     * Fonction d'initialisation du servlet.
     * Appelé dans tomcat.
     */
    @Override
    public void init()
    {
        staticFileLocation("/static");

        Map<String, String> templateVariables = new HashMap<>();
        templateVariables.put("title", "Hello");
        templateVariables.put("hello_content", "Hello, world !");

        get("/", (req, res) -> new ModelAndView(templateVariables, "hello.hbs"), templateEngine);
        
        new TasksController(new TasksService());
    }
}
