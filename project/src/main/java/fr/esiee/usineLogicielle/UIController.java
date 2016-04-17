package fr.esiee.usineLogicielle;

import fr.esiee.usineLogicielle.routes.ui.HomeRoute;
import fr.esiee.usineLogicielle.routes.ui.TaskCreateRoute;
import fr.esiee.usineLogicielle.routes.ui.TaskEditRoute;
import fr.esiee.usineLogicielle.routes.ui.TaskShowRoute;
import fr.esiee.usineLogicielle.routes.ui.TasksListRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.get;

/**
 * Classe d'initialisation des routes pour l'application
 */
public final class UIController
{
	/** Template engine a utiliser */
    private static HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

    /** constructeur */
    private UIController(){}

    /**
     * Init function
     */
    public static void init()
    {
        get("/", new HomeRoute(), templateEngine);
        get("/tasks", new TasksListRoute(), templateEngine);
        get("/home", new HomeRoute(), templateEngine);
        get("/create", new TaskCreateRoute(), templateEngine);
        get("/edit", new TaskEditRoute(), templateEngine);
        get("/show", new TaskShowRoute(), templateEngine);
    }
}
