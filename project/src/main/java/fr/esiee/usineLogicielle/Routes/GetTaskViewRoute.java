package fr.esiee.usineLogicielle.Routes;

import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Route appellée par spark qui va procéder à la récupération d'une tâche précise en BDD.
 * 
 * @author perdigao
 *
 */
public class GetTaskViewRoute implements Route 
{
	/**
	 * Le modèle du back-end
	 */
	private TasksService model;
	
	/**
	 * Constructeur
	 * 
	 * @param model Le modèle du back-end
	 */
	public GetTaskViewRoute(TasksService model)
	{
		this.model = model;
	}

	/**
	 * Fonction de routage du webService, appelle le modèle du back end.
	 * 
	 * @param request Objet spark représentant la requête de l'utilisateur en HTTP.
	 * @param response (non utilisée) Objet spark représentant la réponse en HTTP avec le code
	 * @return le résultat de la requête utilisateur pour ce web service.
	 */
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
