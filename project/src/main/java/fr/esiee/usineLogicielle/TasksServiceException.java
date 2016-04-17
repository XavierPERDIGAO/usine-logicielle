package fr.esiee.usineLogicielle;

/**
 * Erreur specifique pour {@link TasksService}
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
