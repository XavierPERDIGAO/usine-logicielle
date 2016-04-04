package fr.esiee.usineLogicielle;

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

/**
 * Test sur JDBC
 * Utilisation de la librairie DBUnit.
 * 
 * @author perdigao
 *
 */
public class DBTest
{

	/**
	 * Driver de la librairie DBUnit pour MySQL.
	 */
	private static final String JDBC_DRIVER = org.gjt.mm.mysql.Driver.class.getName();
	
	/**
	 * Url de la base de données de test MySQL.
	 */
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/TasksTest";
	
	/**
	 * Id de l'utilisateur de la BDD de test.
	 */
	private static final String USER = "root";
	
	/**
	 * Password de l'utilisateur de la BDD de test.
	 */
	private static final String PASSWORD = "root";

	/**
	 * importe les données de test contenu dans le fichier XML dataset.xml
	 * 
	 * @throws Exception
	 */
	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsert(dataSet);
	}

	private IDataSet readDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new File("src/test/resources/dataset.xml"));
	}

	private void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	/**
	 * Test la méthode getTaskList du modèle.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTaskListTest() throws Exception 
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(tasks.size(), 3);
		
		Assert.assertEquals(tasks.get(0).getId(), 1);
		Assert.assertEquals(tasks.get(0).getTitle(), "Tache1 : Test");
		Assert.assertEquals(tasks.get(0).getBody(), "Ceci est le premier test");
		
		Assert.assertEquals(tasks.get(1).getId(), 2);
		Assert.assertEquals(tasks.get(1).getTitle(), "Tache2 : Test");
		Assert.assertEquals(tasks.get(1).getBody(), "Ceci est le deuxième test");
		
		Assert.assertEquals(tasks.get(2).getId(), 3);
		Assert.assertEquals(tasks.get(2).getTitle(), "Tache3 : Test");
		Assert.assertEquals(tasks.get(2).getBody(), "Ceci est le troisième test");
	}
	
	/**
	 * Test la méthode getTaskByID du modèle.
	 * Deux tests : 1) récupère bien les objets existant dans la base
	 * 				2) retourne une erreur quand la tache n'existe pas en BDD
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTaskByID() throws Exception 
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		Task task1 = repository.getTaskByID(1);
		Task task2 = repository.getTaskByID(2);
		Task task42 = repository.getTaskByID(42);
		
		Assert.assertEquals(task1.getId(), 1);
		Assert.assertEquals(task1.getTitle(), "Tache1 : Test");
		Assert.assertEquals(task1.getBody(), "Ceci est le premier test");
		
		Assert.assertEquals(task2.getId(), 2);
		Assert.assertEquals(task2.getTitle(), "Tache2 : Test");
		Assert.assertEquals(task2.getBody(), "Ceci est le deuxième test");
		
		Assert.assertEquals(task42, null);
	}
	
	/**
	 * Test qui vérifie la méthode addTask du modèle.
	 * Retourne une erreur si la tache fournie en entrée est null.
	 * 
	 * @throws Exception
	 */
	@Test
	public void addTaskTest() throws Exception
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		Task task42 = new Task();
		task42.setId(-1);
		task42.setTitle("Tache num 42");
		task42.setBody("la tache imaginaire");
		
		int result = repository.addTask(task42);
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(result, 0);
		Assert.assertEquals(tasks.size(), 4);
		
		Task t = tasks.get(3);
		
		Assert.assertEquals(t.getTitle(), "Tache num 42");
		Assert.assertEquals(t.getBody(), "la tache imaginaire");
		
		Task fake = null;
		result = repository.addTask(fake);
		Assert.assertEquals(result, -1);
	}
	
	/**
	 * Test qui vérifie la méthode editTask du modèle.
	 * Retourne une erreur si la tache fournie en entrée est null.
	 * 
	 * @throws Exception
	 */
	@Test
	public void editTaskTest() throws Exception
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		Task task1 = new Task();
		task1.setId(1);
		task1.setTitle("Tache1 : Test Modifié");
		task1.setBody("voila, c'est changé!");
		
		int result = repository.editTask(task1);
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(result, 0);
		Assert.assertEquals(tasks.size(), 3);
		
		Task t = repository.getTaskByID(1);
		
		Assert.assertEquals(t.getTitle(), "Tache1 : Test Modifié");
		Assert.assertEquals(t.getBody(), "voila, c'est changé!");
		
		Task fake = null;
		result = repository.editTask(fake);
		Assert.assertEquals(result, -1);
	}
	
	/**
	 * Test qui vérifie la méthode deleteTask du modèle.
	 * Vérifie que la suppression d'une tache inexistante ne modifie pas le contenu de la base de données.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteTaskTest() throws Exception
	{
		TasksService repository = new TasksService(JDBC_URL, USER, PASSWORD);
		
		int result = repository.deleteTask(1);
		List<Task> tasks = repository.getTaskList();
		
		Assert.assertEquals(result, 0);
		Assert.assertEquals(tasks.size(), 2);
		
		Assert.assertEquals(tasks.get(0).getId(), 2);
		Assert.assertEquals(tasks.get(0).getTitle(), "Tache2 : Test");
		Assert.assertEquals(tasks.get(0).getBody(), "Ceci est le deuxième test");
		
		Assert.assertEquals(tasks.get(1).getId(), 3);
		Assert.assertEquals(tasks.get(1).getTitle(), "Tache3 : Test");
		Assert.assertEquals(tasks.get(1).getBody(), "Ceci est le troisième test");
		
		//Effacer une tache qui n'existe pas : pas de changement dans la liste.
		result = repository.deleteTask(42);
		tasks = repository.getTaskList();
		Assert.assertEquals(tasks.size(), 2);
	}
}
