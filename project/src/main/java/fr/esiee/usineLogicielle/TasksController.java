package fr.esiee.usineLogicielle;

import spark.ResponseTransformer;
import static spark.Spark.*;
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
		get("/tasks-list", new Route() {
		      @Override
		      public Object handle(Request request, Response response) 
		      {
		    	  return tasksService.getTaskList();
		      }
	    }, JsonUtil.json());
		    
	    //retourne une tache precise
	    get("/task-view/:id", new Route() {
		      @Override
		      public Object handle(Request request, Response response) 
		      {
		    	  int id = Integer.parseInt(request.params(":id"));
		    	  Task task = tasksService.getTaskByID(id);
		    	  if (task != null) {
		    		    return task;
		    	  }
		    	  response.status(400);
		    	  return new ResponseError("No user with id '%s' found", request.params(":id"));
		      }
		}, JsonUtil.json());
	    
	    //edite une tache de la liste
	    put("/task-edit/:Task", new Route() {
		      @Override
		      public Object handle(Request request, Response response) 
		      {
		    	  String temp = request.params(":Task");
		    	  Task task = JsonUtil.fromJson(temp);
		    	  tasksService.editTask(task);
		    	  return "ok";
		      }
		}, JsonUtil.json());
	    
	    //ajoute une tache a la liste
	    post("/task-create/:Task", new Route() {
		      @Override
		      public Object handle(Request request, Response response) 
		      {
		    	  String temp = request.params(":Task");
		    	  Task task = JsonUtil.fromJson(temp);
		    	  tasksService.addTask(task);
		    	  return "ok";
		      }
		}, JsonUtil.json());
	    
	    //supprime une tache
	    delete("/task-delete/:id", new Route() {
		      @Override
		      public Object handle(Request request, Response response) 
		      {
		    	  int id = Integer.parseInt(request.params(":id"));
		    	  tasksService.deleteTask(id);
		    	  return "ok";
		      }
		}, JsonUtil.json());
	    
	    //renvoie l'erreur
	    exception(IllegalArgumentException.class, (e, req, res) -> {
	    	  res.status(400);
	    	  res.body(JsonUtil.toJson(new ResponseError(e)));
	    });
	  }
}
