package fr.esiee.usineLogicielle;

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
	String url = "jdbc:mysql://127.0.0.1:3306/Tasks";
	String user = "root";
	String password = "El/fuerte31";
	
	public TasksService() {}
	public TasksService(String url, String user, String password) 
	{
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
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
