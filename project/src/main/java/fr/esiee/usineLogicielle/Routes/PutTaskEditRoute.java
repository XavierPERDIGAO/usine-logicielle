package fr.esiee.usineLogicielle.Routes;

import com.google.gson.JsonSyntaxException;

import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Route appellée par spark qui va procéder à la modification des valeurs d'une tâche en BDD.
 * 
 * @author perdigao
 *
 */
public class PutTaskEditRoute implements Route 
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
	public PutTaskEditRoute(TasksService model)
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
