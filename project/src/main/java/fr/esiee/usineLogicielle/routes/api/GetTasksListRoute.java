package fr.esiee.usineLogicielle.routes.api;
import fr.esiee.usineLogicielle.TasksService;
import fr.esiee.usineLogicielle.TasksServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;


/**
 * Route appellee par spark qui va recuperer la liste des taches sauvegardees en BDD.
 * 
 * @author perdigao
 *
 */
public class GetTasksListRoute implements Route
{
    private final static Logger logger = LoggerFactory.getLogger(GetTasksListRoute.class);

	/**
	 * Le modele du back-end
	 */
	private TasksService model;
	
	/**
	 * Constructeur
	 * 
	 * @param model Le modele du back-end
	 */
	public GetTasksListRoute(TasksService model)
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
