package fr.esiee.usineLogicielle;

import static spark.Spark.*;

import fr.esiee.usineLogicielle.routes.api.DeleteTaskRoute;
import fr.esiee.usineLogicielle.routes.api.GetTaskViewRoute;
import fr.esiee.usineLogicielle.routes.api.GetTasksListRoute;
import fr.esiee.usineLogicielle.routes.api.PostAddTaskRoute;
import fr.esiee.usineLogicielle.routes.api.PutTaskEditRoute;

/**
 * Le controlleur du Back-end du projet.
 *
 * @author perdigao
 */
public final class ApiController
{
    /**
     * Constructeur privé. Classe utilitaire.
     */
    private ApiController(){}

    /**
     * appelle chaque fonction de routage des webs services avec la librairie java spark.
     *
     * @param tasksService Le modèle du back-end du projet.
     */
    public static void init(final TasksService tasksService)
    {
        //retourne la liste des taches
        get("/api/tasks-list", new GetTasksListRoute(tasksService), JsonUtil.json());

        //retourne une tache precise
        get("/api/task-view/:id", new GetTaskViewRoute(tasksService), JsonUtil.json());

        //edite une tache de la liste
        put("/api/task-edit/", new PutTaskEditRoute(tasksService), JsonUtil.json());

        //ajoute une tache a la liste
        post("/api/task-create", new PostAddTaskRoute(tasksService), JsonUtil.json());

        //supprime une tache
        delete("/api/task-delete/:id", new DeleteTaskRoute(tasksService), JsonUtil.json());
    }
}
