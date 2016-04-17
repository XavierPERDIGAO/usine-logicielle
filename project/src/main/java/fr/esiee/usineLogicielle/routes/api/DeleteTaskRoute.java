package fr.esiee.usineLogicielle.routes.api;

import fr.esiee.usineLogicielle.TasksService;
import fr.esiee.usineLogicielle.TasksServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Route appellee par spark qui va proceder a la suppression d'une tache en BDD
 * 
 * @author perdigao
 *
 */
public class DeleteTaskRoute implements Route
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
	public DeleteTaskRoute(TasksService model)
	{
		this.model = model;
	}
	

	/**
	 * Fonction de routage du webService, appelle le modele du back end.
	 * 
	 * @param request Objet spark representant la requete de l'utilisateur en HTTP.
	 * @param response Objet spark representant la reponse en HTTP avec le code
	 * @return le resultat de la requete utilisateur pour ce web service.
	 */
	@Override
	public Object handle(Request request, Response response) throws Exception 
	{
        response.type("application/json");
        int id = Integer.parseInt(request.params(":id"));
        try{
            model.deleteTask(id);
            response.status(200);
            return "ok";
        }
        catch(TasksServiceException e)
        {
            logger.error("Unable to delete task " + id, e);
            response.status(500);
            return "error";
        }
	}

}
