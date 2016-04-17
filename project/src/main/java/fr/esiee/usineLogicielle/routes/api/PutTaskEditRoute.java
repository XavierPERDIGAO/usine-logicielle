package fr.esiee.usineLogicielle.routes.api;

import com.google.gson.JsonSyntaxException;

import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import fr.esiee.usineLogicielle.TasksServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Route appellee par spark qui va proceder a la modification des valeurs d'une tache en BDD.
 *
 * @author perdigao
 */
public class PutTaskEditRoute implements Route
{
    private final static Logger logger = LoggerFactory.getLogger(PutTaskEditRoute.class);

    /**
     * Le modele du back-end
     */
    private TasksService model;

    /**
     * Constructeur
     *
     * @param model Le modele du back-end
     */
    public PutTaskEditRoute(TasksService model)
    {
        this.model = model;
    }

    /**
     * Fonction de routage du webService, appelle le modele du back end.
     *
     * @param request  Objet spark representant la requete de l'utilisateur en HTTP.
     * @param response (non utilisee) Objet spark representant la reponse en HTTP avec le code
     * @return le resultat de la requete utilisateur pour ce web service.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        response.type("application/json");
        String temp = request.body();

        try
        {
            Task task = JsonUtil.fromJson(temp);
            model.editTask(task);
            response.status(200);
            return "ok";
        }
        catch(JsonSyntaxException e)
        {
            logger.error("Unable to edit task: bad JSON format", e);
            response.status(500);
            return "Impossible d'editer la tache: mauvais format JSON";
        }
        catch(TasksServiceException e)
        {
            logger.error("Unable to edit task: task server error", e);
            response.status(500);
            return "error";
        }
    }

}
