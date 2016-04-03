package fr.esiee.usineLogicielle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TasksService 
{
	Properties options = new Properties();
	String url = "jdbc:mysql://127.0.0.1:3306/Tasks?user=root&password=El/fuerte31";
	
	public TasksService()
	{
		options.put("driver", "com.mysql.jdbc.Driver");
	}
	
	public List<Task> getTaskList()
	{
		Connection connection = null;
		Statement state = null;
		List<Task> tasks = new ArrayList<Task>();
		
		try
		{
			connection = DriverManager.getConnection(url);
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
			connection = DriverManager.getConnection(url);
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
		Connection connection = null;
		Statement state = null;
		
		try
		{
			connection = DriverManager.getConnection(url);
			state = connection.createStatement();
			String query = "INSERT INTO Task(title, body) VALUES ('" + newTask.title + "','" + newTask.body +"')";
			int result = state.executeUpdate(query);
			if (result == Statement.EXECUTE_FAILED)
				System.out.println("erreur BDD");
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
		Connection connection = null;
		Statement state = null;
		
		try
		{
			connection = DriverManager.getConnection(url);
			state = connection.createStatement();
			String query = "UPDATE Task SET title = '" + modifiedTask.title + "', body = '" + modifiedTask.body + "' WHERE id = " + modifiedTask.id + ";";
			int result = state.executeUpdate(query);
			if (result == Statement.EXECUTE_FAILED)
				System.out.println("erreur BDD");
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
			connection = DriverManager.getConnection(url);
			state = connection.createStatement();
			String query = "DELETE FROM Task WHERE id = " + idTask + ";";
			int result = state.executeUpdate(query);
			if (result == Statement.EXECUTE_FAILED)
				System.out.println("erreur BDD");
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
