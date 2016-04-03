package fr.esiee.usineLogicielle.Test;

import java.io.File;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;

public class DBTests {

	private static final String JDBC_DRIVER = org.gjt.mm.mysql.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/TasksTest";
	private static final String USER = "root";
	private static final String PASSWORD = "El/fuerte31";

	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsert(dataSet);
	}

	private IDataSet readDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new File("src/main/java/fr/esiee/usineLogicielle/Test/dataset.xml"));
	}

	private void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	@Test
	public void getTaskListTest() throws Exception 
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(tasks.size(), 3);
		
		Assert.assertEquals(tasks.get(0).id, 1);
		Assert.assertEquals(tasks.get(0).title, "Tache1 : Test");
		Assert.assertEquals(tasks.get(0).body, "Ceci est le premier test");
		
		Assert.assertEquals(tasks.get(1).id, 2);
		Assert.assertEquals(tasks.get(1).title, "Tache2 : Test");
		Assert.assertEquals(tasks.get(1).body, "Ceci est le deuxième test");
		
		Assert.assertEquals(tasks.get(2).id, 3);
		Assert.assertEquals(tasks.get(2).title, "Tache3 : Test");
		Assert.assertEquals(tasks.get(2).body, "Ceci est le troisième test");
	}
	
	@Test
	public void getTaskByID() throws Exception 
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		Task task1 = repository.getTaskByID(1);
		Task task2 = repository.getTaskByID(2);
		Task task42 = repository.getTaskByID(42);
		
		Assert.assertEquals(task1.id, 1);
		Assert.assertEquals(task1.title, "Tache1 : Test");
		Assert.assertEquals(task1.body, "Ceci est le premier test");
		
		Assert.assertEquals(task2.id, 2);
		Assert.assertEquals(task2.title, "Tache2 : Test");
		Assert.assertEquals(task2.body, "Ceci est le deuxième test");
		
		Assert.assertEquals(task42, null);
	}
	
	@Test
	public void addTaskTest() throws Exception
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		Task task42 = new Task();
		task42.id = -1;
		task42.title = "Tache num 42";
		task42.body = "la tache imaginaire";
		
		int result = repository.addTask(task42);
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(result, 0);
		Assert.assertEquals(tasks.size(), 4);
		
		Task t = tasks.get(3);
		
		Assert.assertEquals(t.title, "Tache num 42");
		Assert.assertEquals(t.body, "la tache imaginaire");
		
		Task fake = null;
		result = repository.addTask(fake);
		Assert.assertEquals(result, -1);
	}
	
	@Test
	public void editTaskTest() throws Exception
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		Task task1 = new Task();
		task1.id = 1;
		task1.title = "Tache1 : Test Modifié";
		task1.body = "voila, c'est changé!";
		
		int result = repository.editTask(task1);
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(result, 0);
		Assert.assertEquals(tasks.size(), 3);
		
		Task t = repository.getTaskByID(1);
		
		Assert.assertEquals(t.title, "Tache1 : Test Modifié");
		Assert.assertEquals(t.body, "voila, c'est changé!");
		
		Task fake = null;
		result = repository.addTask(fake);
		Assert.assertEquals(result, -1);
	}
	
	@Test
	public void deleteTaskTest() throws Exception
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		int result = repository.deleteTask(1);
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(result, 0);
		Assert.assertEquals(tasks.size(), 2);
		
		Assert.assertEquals(tasks.get(0).id, 2);
		Assert.assertEquals(tasks.get(0).title, "Tache2 : Test");
		Assert.assertEquals(tasks.get(0).body, "Ceci est le deuxième test");
		
		Assert.assertEquals(tasks.get(1).id, 3);
		Assert.assertEquals(tasks.get(1).title, "Tache3 : Test");
		Assert.assertEquals(tasks.get(1).body, "Ceci est le troisième test");
		
		//Effacer une tache qui n'existe pas : pas de changement dans la liste.
		result = repository.deleteTask(42);
		tasks = repository.getTaskList();
		Assert.assertEquals(tasks.size(), 2);
		
		Task fake = null;
		result = repository.addTask(fake);
		Assert.assertEquals(result, -1);
	}
}
