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
 * Route appellée par spark qui va procéder à la modification des valeurs d'une tâche en BDD.
 *
 * @author perdigao
 */
public class PutTaskEditRoute implements Route
{
    private final static Logger logger = LoggerFactory.getLogger(PutTaskEditRoute.class);

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
     * @param request  Objet spark représentant la requête de l'utilisateur en HTTP.
     * @param response (non utilisée) Objet spark représentant la réponse en HTTP avec le code
     * @return le résultat de la requête utilisateur pour ce web service.
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
            return "Impossible d'éditer la tâche: mauvais format JSON";
        }
        catch(TasksServiceException e)
        {
            logger.error("Unable to edit task: task server error", e);
            response.status(500);
            return "error";
        }
    }

}
