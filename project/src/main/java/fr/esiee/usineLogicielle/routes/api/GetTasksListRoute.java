package fr.esiee.usineLogicielle.routes.api;
import fr.esiee.usineLogicielle.TasksService;
import fr.esiee.usineLogicielle.TasksServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Arrays;


/**
 * Route appellée par spark qui va récupérer la liste des tâches sauvegardées en BDD.
 * 
 * @author perdigao
 *
 */
public class GetTasksListRoute implements Route
{
    private final static Logger logger = LoggerFactory.getLogger(GetTasksListRoute.class);

	/**
	 * Le modèle du back-end
	 */
	private TasksService model;
	
	/**
	 * Constructeur
	 * 
	 * @param model Le modèle du back-end
	 */
	public GetTasksListRoute(TasksService model)
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
			response.type("application/json");
			return model.getTaskList();
		}
		catch(TasksServiceException e)
		{
			logger.error("Error retreiving tasks list", e);
			response.status(500);
			return "Error: " + e.getMessage();
		}
	}

}
