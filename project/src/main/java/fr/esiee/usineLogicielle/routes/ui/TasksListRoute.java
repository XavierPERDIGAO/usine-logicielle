package fr.esiee.usineLogicielle.routes.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class TasksListRoute implements TemplateViewRoute
{
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception
    {
        Map<String, String> templateVariables = new HashMap<>();
        templateVariables.put("title", "Tasks");
        templateVariables.put("page", "tasks");

        return new ModelAndView(templateVariables, "main.hbs");
    }
}
