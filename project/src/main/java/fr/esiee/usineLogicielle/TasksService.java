package fr.esiee.usineLogicielle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class TasksService 
{
	/**
	 * L'url d'appel à la base de données MySQL
	 */
	String url = "jdbc:mysql://127.0.0.1:3306/Tasks";
	
	/**
	 * L'id de l'utilisateur MySQL
	 */
	String user = "root";
	
	/**
	 * Le mot de passe de l'utilisateur MySQL
	 */
	String password = "root";
	
	/**
	 * Constructeur vide, appelle les paramètres MySQL par défaut
	 */
	public TasksService() {}
	
	/**
	 * Constructeur permettant d'entrer les paramètres MySQL
	 * Utilisé dans les Tests Unitaires de cette classe pour ouvrir la BDD de test.
	 * 
	 * @param url l'url de la base de données à ouvrir en MySQL
	 * @param user l'id de l'utilisateur MySQL
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
		Connection connection = null;
		Statement state = null;
		List<Task> tasks = new ArrayList<Task>();
		
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			state = connection.createStatement();
			String query = "SELECT * FROM Task";
			ResultSet result = state.executeQuery(query);
			
		    while(result.next())
		    {
		    	Task t = new Task();
				t.id = result.getInt(1);
				t.title = result.getString(2);
				t.body = result.getString(3);
				tasks.add(t);
		    }
			
			state.close();
			connection.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
		Connection connection = null;
		Statement state = null;
		Task t = null;
		int numberOfTasks = 0;
		
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			state = connection.createStatement();
			String query = "SELECT * FROM Task WHERE id = " + idTask + ";";
			ResultSet result = state.executeQuery(query);
			
		    while(result.next())
		    {
		    	t = new Task();
				t.id = result.getInt(1);
				t.title = result.getString(2);
				t.body = result.getString(3);
				numberOfTasks++;
		    }
			
			state.close();
			connection.close();
			
			if (numberOfTasks > 1)
				throw new Exception("Erreur : plusieurs taches trouvées pour cet indice");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
		if (newTask == null)
			return -1;
		
		Connection connection = null;
		PreparedStatement state = null;
		
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			String query = "INSERT INTO Task(title, body) VALUES (?, ?);";
			state = connection.prepareStatement(query);
			state.setString(1, newTask.title);
			state.setString(2, newTask.body);
			int result = state.executeUpdate();
			if (result == Statement.EXECUTE_FAILED)
			{
				System.out.println("erreur BDD");
				state.close();
				connection.close();
				return -1;
			}
			state.close();
			connection.close();
			return 0;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	/**
	 * Modifier une tache existante dans la base de données.
	 * @param modifiedTask la tache à modifier.
	 * @return 0 pour OK, -1 pour erreur.
	 */
	public int editTask(Task modifiedTask)
	{
		if (modifiedTask == null)
			return -1;
		
		Connection connection = null;
		PreparedStatement state = null;
		
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			
			String query = "UPDATE Task SET title = ?, body = ? WHERE id = ?;";
			state = connection.prepareStatement(query);
			state.setString(1, modifiedTask.title);
			state.setString(2, modifiedTask.body);
			state.setLong(3, modifiedTask.id);
			
			int result = state.executeUpdate();
			if (result == Statement.EXECUTE_FAILED)
			{
				System.out.println("erreur BDD");
				state.close();
				connection.close();
				return -1;
			}
			state.close();
			connection.close();
			return 0;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
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
		Connection connection = null;
		Statement state = null;
		
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			state = connection.createStatement();
			String query = "DELETE FROM Task WHERE id = " + idTask + ";";
			int result = state.executeUpdate(query);
			if (result == Statement.EXECUTE_FAILED)
			{
				System.out.println("erreur BDD");
				state.close();
				connection.close();
				return -1;
			}
			state.close();
			connection.close();
			return 0;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
	}
}
