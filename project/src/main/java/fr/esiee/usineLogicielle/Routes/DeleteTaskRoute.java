package fr.esiee.usineLogicielle.Routes;

import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeleteTaskRoute implements Route 
{

	private TasksService model;
	
	public DeleteTaskRoute(TasksService model)
	{
		this.model = model;
	}
	
	@Override
	public Object handle(Request request, Response response) throws Exception 
	{
		int id = Integer.parseInt(request.params(":id"));
		int result = model.deleteTask(id);
		if (result == 0)
			return "ok";
		else
			return "error";
	}

}
