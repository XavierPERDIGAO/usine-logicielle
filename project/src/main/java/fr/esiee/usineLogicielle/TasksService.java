package fr.esiee.usineLogicielle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.execution.QueryExecution;

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
		JavaSparkContext sc = Main.getSparkContext();
		SQLContext sqlContext = new SQLContext(sc);
		
		DataFrame df = sqlContext.read().jdbc(url, "Task", options);
		List<Row> rows = df.collectAsList();
		List<Task> tasks = new ArrayList<Task>();
		
		for (Row row : rows)
		{
			Task task = new Task();
			task.id = row.getLong(0);
			task.title = row.getString(1);
			task.body = row.getString(2);
			tasks.add(task);
		}
		
		return tasks;
	}
	
	public Task getTaskByID(int idTask)
	{
		JavaSparkContext sc = Main.getSparkContext();
		SQLContext sqlContext = new SQLContext(sc);
		
		DataFrame df = sqlContext.read().jdbc(url, "Task", options);
		Row row = df.filter(df.col("id").eqNullSafe(1)).first();
		
		Task task = new Task();
		task.id = row.getLong(0);
		task.title = row.getString(1);
		task.body = row.getString(2);
		return task;	
	}
	
	public void addTask(Task newTask)
	{
		//permet les injections SQL
		JavaSparkContext sc = Main.getSparkContext();
		SQLContext sqlContext = new SQLContext(sc);
		
		List<Task> temp = new ArrayList<Task>();
		temp.add(newTask);
		DataFrame df = sqlContext.createDataFrame(temp, Task.class);
		
		df.write().mode(SaveMode.Append).jdbc(url, "Task", options);//executeSql("INSERT INTO Task VALUES('" + newTask.title + "','" + newTask.body + "');");
	}
	
	public void editTask(Task modifiedTask)
	{
		//permet les injections SQL
		JavaSparkContext sc = Main.getSparkContext();
		SQLContext sqlContext = new SQLContext(sc);
		sqlContext.setConf(options);
		
		sqlContext.executeSql("UPDATE Task SET title = '" + modifiedTask.title + "', body = '" + modifiedTask.body + "' WHERE id = " + modifiedTask.id + ";");
	}
	
	public void deleteTask(int idTask)
	{
		JavaSparkContext sc = Main.getSparkContext();
		SQLContext sqlContext = new SQLContext(sc);
		sqlContext.setConf(options);
		
		sqlContext.executeSql("DELETE FROM Task WHERE id = " + idTask + ";");
	}
}
