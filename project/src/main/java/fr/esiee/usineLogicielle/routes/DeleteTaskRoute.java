package fr.esiee.usineLogicielle.routes;

import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Route appellée par spark qui va procéder à la suppression d'une tache en BDD
 * 
 * @author perdigao
 *
 */
public class DeleteTaskRoute implements Route
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
	public DeleteTaskRoute(TasksService model)
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
		response.type("application/json");
		int id = Integer.parseInt(request.params(":id"));
		int result = model.deleteTask(id);
		if (result == 0)
			return "ok";
		else
			return "error";
	}

}
