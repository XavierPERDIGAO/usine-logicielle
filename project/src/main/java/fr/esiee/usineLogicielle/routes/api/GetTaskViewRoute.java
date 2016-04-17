package fr.esiee.usineLogicielle.routes.api;

import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Route appellee par spark qui va proceder a la recuperation d'une tache precise en BDD.
 * 
 * @author perdigao
 *
 */
public class GetTaskViewRoute implements Route 
{
	/**
	 * Le modele du back-end
	 */
	private TasksService model;
	
	/**
	 * Constructeur
	 * 
	 * @param model Le modele du back-end
	 */
	public GetTaskViewRoute(TasksService model)
	{
		this.model = model;
	}

	/**
	 * Fonction de routage du webService, appelle le modele du back end.
	 * 
	 * @param request Objet spark representant la requete de l'utilisateur en HTTP.
	 * @param response (non utilisee) Objet spark representant la reponse en HTTP avec le code
	 * @return le resultat de la requete utilisateur pour ce web service.
	 */
	@Override
	public Object handle(Request request, Response response) throws Exception 
	{
		response.type("application/json");
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
