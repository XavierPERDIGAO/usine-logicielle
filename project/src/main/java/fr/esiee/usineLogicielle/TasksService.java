package fr.esiee.usineLogicielle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * L'acces a la base de donnees de l'application
 * 
 * @author perdigao
 *
 */
public class TasksService
{
    private final static Logger logger = LoggerFactory.getLogger(TasksService.class);

    private static final String UPDATE_QUERY = "UPDATE Task SET title = ?, body = ? WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM Task WHERE id = ?;";
    private static final String INSERT_QUERY = "INSERT INTO Task(title, body) VALUES (?, ?);";
    private static final String SELECT_QUERY = "SELECT * FROM Task WHERE id = ?;";
    /**
     * L'url d'appel a la base de donnees MySQL
     */
    private String url;

    /**
     * L'id de l'utilisateur MySQL
     */
    private String user;

    /**
     * Le mot de passe de l'utilisateur MySQL
     */
    private String password;

    /**
     * Constructeur vide, appelle les parametres MySQL par defaut
     */
    public TasksService()
    {
        Properties properties = new Properties();
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                properties.put(key, bundle.getString(key));
            }
        }
        catch(NullPointerException e)
        {
            logger.error("Unable to load file config.properties", e);
        }

        url = properties.getProperty("db.connection.jdbcUrl", "jdbc:mysql://127.0.0.1:3306/Tasks");
        user = properties.getProperty("db.connection.user", "root");
        password = properties.getProperty("db.connection.password", "root");
    }

    /**
     * Constructeur permettant d'entrer les parametres MySQL
     * Utilise dans les Tests Unitaires de cette classe pour ouvrir la BDD de test.
     *
     * @param url      l'url de la base de donnees a ouvrir en MySQL
     * @param user     l'id de l'utilisateur MySQL
     * @param password le password de l'utilisateur MySQL
     */
    public TasksService(String url, String user, String password)
    {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Fonction qui ouvre la base de donnees et recupere la liste de toutes les taches en memoire.
     *
     * @return la liste des taches sauvegardees en BDD.
     */
    public List<Task> getTaskList() throws TasksServiceException
    {
        List<Task> tasks = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, user, password);
            Statement state = connection.createStatement()
        )
        {
            ResultSet result = state.executeQuery("SELECT * FROM Task");

            while(result.next())
            {
                Task t = new Task();
                t.setId(result.getInt(1));
                t.setTitle(result.getString(2));
                t.setBody(result.getString(3));
                tasks.add(t);
            }
        }
        catch(Exception e)
        {
            logger.error("Unable to retreive tasks list", e);
            throw new TasksServiceException("Unable to retreive tasks list", e);
        }

        return tasks;
    }

    /**
     * Recupere dans la BDD une tache precise en fournissant l'id de la tache a recuperer.
     *
     * @param idTask l'id de la tache a recuperer.
     * @return la tache souhaitee.
     */
    public Task getTaskByID(int idTask) throws TasksServiceException
    {
        Task t = null;
        int numberOfTasks = 0;

        try(
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement state = connection.prepareStatement(SELECT_QUERY)
        )
        {
            state.setInt(1, idTask);
            ResultSet result = state.executeQuery();

            while(result.next())
            {
                t = new Task();
                t.setId(result.getInt(1));
                t.setTitle(result.getString(2));
                t.setBody(result.getString(3));
                numberOfTasks++;
            }

            if(numberOfTasks > 1) throw new TasksServiceException("Error: multiple tasks found for id " + idTask);
        }
        catch(Exception e)
        {
            logger.error("Unable to retreive task {}", idTask, e);
            throw new TasksServiceException("Unable to retreive task " + idTask, e);
        }

        return t;
    }

    /**
     * Ajouter une tache dans la base de donnees.
     * l'id de la tache a sauvegarder n'est pas prise en compte.
     *
     * @param newTask la tache a sauvegarder
     */
    public void addTask(Task newTask) throws TasksServiceException
    {
        if(newTask == null) throw new TasksServiceException("Unable to create task");

        try(
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement state = connection.prepareStatement(INSERT_QUERY)
        )
        {

            state.setString(1, newTask.getTitle());
            state.setString(2, newTask.getBody());
            int result = state.executeUpdate();
            if(result == Statement.EXECUTE_FAILED)
            {
                logger.error("Unable to create task");
                throw new TasksServiceException("Unable to create task");
            }
        }
        catch(Exception e)
        {
            logger.error("Unable to create task", e);
            throw new TasksServiceException("Unable to create task", e);
        }
    }

    /**
     * Modifier une tache existante dans la base de donnees.
     *
     * @param modifiedTask la tache a modifier.
     */
    public void editTask(Task modifiedTask) throws TasksServiceException
    {
        if(modifiedTask == null) throw new TasksServiceException("Unable to edit task: null variable");

        try(
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement state = connection.prepareStatement(UPDATE_QUERY)
        )
        {
            state.setString(1, modifiedTask.getTitle());
            state.setString(2, modifiedTask.getBody());
            state.setLong(3, modifiedTask.getId());

            int result = state.executeUpdate();
            if(result == Statement.EXECUTE_FAILED)
            {
                logger.error("Unable to edit task {}", modifiedTask.getId());
                throw new TasksServiceException("Unable to edit task " + modifiedTask.getId());
            }
        }
        catch(Exception e)
        {
            logger.error("Unable to edit task {}", modifiedTask.getId(), e);
            throw new TasksServiceException("Unable to edit task " + modifiedTask.getId(), e);
        }
    }

    /**
     * Supprimer une tache de la base de donnees.
     *
     * @param idTask l'id de la tache a supprimer.
     */
    public void deleteTask(int idTask) throws TasksServiceException
    {
        try(
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement state = connection.prepareStatement(DELETE_QUERY)
        )
        {
            state.setInt(1, idTask);
            int result = state.executeUpdate();
            if(result == Statement.EXECUTE_FAILED)
            {
                logger.error("unable to delete task {}", idTask);
                throw new TasksServiceException("Unable to edit task " + idTask);
            }
        }
        catch(Exception e)
        {
            logger.error("unable to delete task {}", idTask, e);
            throw new TasksServiceException("Unable to edit task " + idTask, e);
        }
    }
}
