package fr.esiee.usineLogicielle;

import spark.ResponseTransformer;

import com.google.gson.Gson;

/**
 * Classe possedant des methodes statiques permettant de manipuler le format Json.
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
     * fonction permettant de serialiser un objet en Json.
     *
     * @param object L'objet a serialiser.
     * @return la chaine de caractere contenant l'objet code en Json.
     */
    public static String toJson(Object object)
    {
        return jsonFormatter.toJson(object);
    }

    /**
     * fonction permettant de recuperer un objet code en Json.
     *
     * @param s la chaine de caractere codee en Json.
     * @return l'objet Tache deserialise.
     */
    public static Task fromJson(String s)
    {
        return jsonFormatter.fromJson(s, Task.class);
    }

    /**
     * fonction appelee dans les methodes de routage de Spark. Permet d'appeler la methode toJson automatiquement sur l'objet retourne par les fonctions de route.
     *
     * @return l'objet retourne par le routage, serialise en json.
     */
    public static ResponseTransformer json()
    {
        return JsonUtil::toJson;
    }
}
