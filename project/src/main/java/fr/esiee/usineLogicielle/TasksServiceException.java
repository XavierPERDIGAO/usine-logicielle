package fr.esiee.usineLogicielle;

/**
 * Erreur spécifique pour {@link TasksService}
 */
public class TasksServiceException extends Exception
{
    public TasksServiceException(String message)
    {
        super(message);
    }

    public TasksServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
