package fr.esiee.usineLogicielle;

import spark.servlet.SparkApplication;

import static spark.Spark.staticFileLocation;

/**
 * Entree du programme
 *
 * @author perdigao
 */
public class Main implements SparkApplication
{
    /**
     * juste pour le test.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        SparkApplication application = new Main();
        application.init();
    }

    /**
     * Fonction d'initialisation du servlet.
     * Appele dans tomcat.
     */
    @Override
    public void init()
    {
        staticFileLocation("/static");
        ApiController.init(new TasksService());
        UIController.init();
    }
}
