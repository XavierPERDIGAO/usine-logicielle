package fr.esiee.usineLogicielle;

/**
 * Le modele d'une tache.
 *
 * @author perdigao
 */
public class Task
{
    private long id;
    private String title;
    private String body;

    /**
     * @return l'id d'une tache.
     */
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * @return le titre de la tache.
     */
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return le texte contenu dans la tache.
     */
    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
