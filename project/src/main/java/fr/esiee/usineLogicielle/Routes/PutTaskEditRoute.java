package fr.esiee.usineLogicielle.Routes;

import com.google.gson.JsonSyntaxException;

import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

public class PutTaskEditRoute implements Route 
{

	private TasksService model;
	
	public PutTaskEditRoute(TasksService model)
	{
		this.model = model;
	}
	
	@Override
	public Object handle(Request request, Response response) throws Exception 
	{
		String temp = request.body();
		
		try
		{
			Task task = JsonUtil.fromJson(temp);
			int result = model.editTask(task);
			if (result == 0)
				return "ok";
			else
				return "error";
		}
		catch (JsonSyntaxException e)
		{
			return "La tache envoyée est dans un format json incorrect";
		}
	}

}
