package fr.esiee.usineLogicielle;

import spark.ResponseTransformer;

import com.google.gson.Gson;

/**
 * Classe possédant des méthodes statiques permettant de manipuler le format Json.
 *
 * @author perdigao
 */
public final class JsonUtil
{
    private static Gson jsonFormatter = new Gson();
    /**
     * Classe utilitaire
     */
    private JsonUtil(){}

    /**
     * fonction permettant de sérialiser un objet en Json.
     *
     * @param object L'objet à sérialiser.
     * @return la chaine de caractère contenant l'objet codé en Json.
     */
    public static String toJson(Object object)
    {
        return jsonFormatter.toJson(object);
    }

    /**
     * fonction permettant de récupérer un objet codé en Json.
     *
     * @param s la chaine de caractère codée en Json.
     * @return l'objet Tâche désérialisé.
     */
    public static Task fromJson(String s)
    {
        return jsonFormatter.fromJson(s, Task.class);
    }

    /**
     * fonction appelée dans les méthodes de routage de Spark. Permet d'appeler la méthode toJson automatiquement sur l'objet retourné par les fonctions de route.
     *
     * @return l'objet retourné par le routage, sérialisé en json.
     */
    public static ResponseTransformer json()
    {
        return JsonUtil::toJson;
    }
}
