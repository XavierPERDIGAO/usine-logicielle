package fr.esiee.usineLogicielle.routes.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * Route appellee par spark pour rendre la page Home
 */
public class HomeRoute implements TemplateViewRoute
{
	/**
	 * Fonction de routage du WebService
	 * 
	 * @param request Objet spark representant la requete de l'utilisateur en HTTP.
	 * @param response Objet spark representant la reponse en HTTP avec le code
	 * @return le modele et la vue obtenus pour cette requete
	 */
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception
    {
        Map<String, String> templateVariables = new HashMap<>();
        templateVariables.put("title", "Home");
        templateVariables.put("page", "home");

        return new ModelAndView(templateVariables, "main.hbs");
    }
}
