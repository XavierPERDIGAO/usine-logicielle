package fr.esiee.usineLogicielle;

import static spark.Spark.*;
import fr.esiee.usineLogicielle.Routes.DeleteTaskRoute;
import fr.esiee.usineLogicielle.Routes.GetTaskViewRoute;
import fr.esiee.usineLogicielle.Routes.GetTasksListRoute;
import fr.esiee.usineLogicielle.Routes.PostAddTaskRoute;
import fr.esiee.usineLogicielle.Routes.PutTaskEditRoute;
import spark.Request;
import spark.Response;
import spark.Route;

public class TasksController 
{
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
	    post("/api/task-create/:Task", new PostAddTaskRoute(tasksService), JsonUtil.json());
	    
	    //supprime une tache
	    delete("/api/task-delete/:id", new DeleteTaskRoute(tasksService), JsonUtil.json());
	    
	    //renvoie l'erreur
	    exception(IllegalArgumentException.class, (e, req, res) -> {
	    	  res.status(400);
	    	  res.body(JsonUtil.toJson(new ResponseError(e)));
	    });
	  }
}
