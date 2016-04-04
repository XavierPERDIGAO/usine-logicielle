package fr.esiee.usineLogicielle.Routes;

import java.util.Map;

import com.google.gson.JsonSyntaxException;

import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostAddTaskRoute implements Route 
{

	private TasksService model;
	
	public PostAddTaskRoute(TasksService model)
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
			int result = model.addTask(task);
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
