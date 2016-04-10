package fr.esiee.usineLogicielle;

import fr.esiee.usineLogicielle.routes.ui.HomeRoute;
import fr.esiee.usineLogicielle.routes.ui.TasksListRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.get;

/**
 * Classe d'initialisation des routes pour l'application
 */
public final class UIController
{
    private static HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

    private UIController(){}

    /**
     *
     */
    public static void init()
    {
        get("/", new HomeRoute(), templateEngine);
        get("/tasks", new TasksListRoute(), templateEngine);
        get("/home", new HomeRoute(), templateEngine);
    }
}
