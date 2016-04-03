package fr.esiee.usineLogicielle.Routes;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;


public class GetTasksListRoute implements Route
{
	private TasksService model;
	
	public GetTasksListRoute(TasksService model)
	{
		this.model = model;
	}
	
	@Override
	public Object handle(Request request, Response response) throws Exception 
	{
  	  	return model.getTaskList();
	}

}
