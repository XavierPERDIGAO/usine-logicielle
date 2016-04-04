package fr.esiee.usineLogicielle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class TasksService
{
    private final static Logger logger = LoggerFactory.getLogger(TasksService.class);

    private static final String UPDATE_QUERY = "UPDATE Task SET title = ?, body = ? WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM Task WHERE id = ?;";
    private static final String INSERT_QUERY = "INSERT INTO Task(title, body) VALUES (?, ?);";
    private static final String SELECT_QUERY = "SELECT * FROM Task WHERE id = ?;";
    /**
     * L'url d'appel à la base de données MySQL
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
     * Constructeur vide, appelle les paramètres MySQL par défaut
     */
    public TasksService()
    {
        Properties properties = new Properties();
        InputStream stream = this.getClass().getResourceAsStream("config.properties");
        try
        {
            properties.load(stream);
        }
        catch(IOException | NullPointerException | IllegalStateException e)
        {
            logger.error("Unable to load file config.properties", e);
        }

        url = properties.getProperty("db.connection.jdbcUrl", "jdbc:mysql://127.0.0.1:3306/TasksTest");
        user = properties.getProperty("db.connection.user", "root");
        url = properties.getProperty("db.connection.password", "root");
    }

    /**
     * Constructeur permettant d'entrer les paramètres MySQL
     * Utilisé dans les Tests Unitaires de cette classe pour ouvrir la BDD de test.
     *
     * @param url      l'url de la base de données à ouvrir en MySQL
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
     * Fonction qui ouvre la base de données et récupère la liste de toutes les taches en mémoire.
     *
     * @return la liste des taches sauvegardées en BDD.
     */
    public List<Task> getTaskList()
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
            logger.debug("Unable to retreive tasks list", e);
        }

        return tasks;
    }

    /**
     * Récupère dans la BDD une tache précise en fournissant l'id de la tache à récupérer.
     *
     * @param idTask l'id de la tache a récupérer.
     * @return la tache souhaitée.
     */
    public Task getTaskByID(int idTask)
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

            if(numberOfTasks > 1) throw new Exception("Erreur : plusieurs taches trouvées pour cet indice");
        }
        catch(Exception e)
        {
            logger.error("Unable to retreive task {}", idTask, e);
        }

        return t;
    }

    /**
     * Ajouter une tache dans la base de données.
     * l'id de la tache à sauvegarder n'est pas prise en compte.
     *
     * @param newTask la tache à sauvegarder
     * @return 0 pour OK, -1 pour erreur.
     */
    public int addTask(Task newTask)
    {
        if(newTask == null) return -1;

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
                return -1;
            }
            return 0;
        }
        catch(Exception e)
        {
            logger.error("Unable to create task", e);
            return -1;
        }
    }

    /**
     * Modifier une tache existante dans la base de données.
     *
     * @param modifiedTask la tache à modifier.
     * @return 0 pour OK, -1 pour erreur.
     */
    public int editTask(Task modifiedTask)
    {
        if(modifiedTask == null) return -1;

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
                return -1;
            }
            return 0;
        }
        catch(Exception e)
        {
            logger.error("Unable to edit task {}", modifiedTask.getId(), e);
            return -1;
        }
    }

    /**
     * Supprimer une tache de la base de données.
     *
     * @param idTask l'id de la tache a supprimer.
     * @return 0 pour OK, -1 pour erreur.
     */
    public int deleteTask(int idTask)
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
                return -1;
            }
            return 0;
        }
        catch(Exception e)
        {
            logger.error("unable to delete task {}", idTask, e);
            return -1;
        }
    }
}
