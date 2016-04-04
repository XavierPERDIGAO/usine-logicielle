package fr.esiee.usineLogicielle;

import static spark.Spark.*;
import fr.esiee.usineLogicielle.Routes.DeleteTaskRoute;
import fr.esiee.usineLogicielle.Routes.GetTaskViewRoute;
import fr.esiee.usineLogicielle.Routes.GetTasksListRoute;
import fr.esiee.usineLogicielle.Routes.PostAddTaskRoute;
import fr.esiee.usineLogicielle.Routes.PutTaskEditRoute;

/**
 * Le controlleur du Back-end du projet.
 * 
 * @author perdigao
 *
 */
public class TasksController 
{
	/**
	 * appelle chaque fonction de routage des webs services avec la librairie java spark.
	 * 
	 * @param tasksService Le modèle du back-end du projet.
	 */
	public TasksController(final TasksService tasksService) 
	{
		//renvoie les donnees au format Json
		after((req, res) -> {
			res.type("application/json");
		});
		  
		//retourne la liste des taches
		get("/api/tasks-list", new GetTasksListRoute(tasksService), JsonUtil.json());
		    
	    //retourne une tache precise
	    get("/api/task-view/:id", new GetTaskViewRoute(tasksService), JsonUtil.json());
	    
	    //edite une tache de la liste
	    put("/api/task-edit/:Task", new PutTaskEditRoute(tasksService), JsonUtil.json());
	    
	    //ajoute une tache a la liste
	    post("/api/task-create", new PostAddTaskRoute(tasksService), JsonUtil.json());
	    
	    //supprime une tache
	    delete("/api/task-delete/:id", new DeleteTaskRoute(tasksService), JsonUtil.json());
	  }
}
