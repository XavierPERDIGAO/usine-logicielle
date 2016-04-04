package fr.esiee.usineLogicielle.Routes;

import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetTaskViewRoute implements Route 
{
	private TasksService model;
	
	public GetTaskViewRoute(TasksService model)
	{
		this.model = model;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception 
	{
		try
		{
		  	int id = Integer.parseInt(request.params(":id"));
		  	Task task = model.getTaskByID(id);
		  	if (task != null) 
		  	{
		  		return task;
		  	}
		  	return "There is no task with id " + id + " found";
		}
		catch (NumberFormatException e)
		{
			return "l'identifiant n'est pas un entier";
		}
	}

}
