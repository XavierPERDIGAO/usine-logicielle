package fr.esiee.usineLogicielle.routes.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class HomeRoute implements TemplateViewRoute
{
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception
    {
        Map<String, String> templateVariables = new HashMap<>();
        templateVariables.put("title", "Home");
        templateVariables.put("page", "home");

        return new ModelAndView(templateVariables, "main.hbs");
    }
}
