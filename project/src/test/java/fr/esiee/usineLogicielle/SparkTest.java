package fr.esiee.usineLogicielle;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import spark.Request;
import spark.Response;
import fr.esiee.usineLogicielle.routes.api.DeleteTaskRoute;
import fr.esiee.usineLogicielle.routes.api.GetTaskViewRoute;
import fr.esiee.usineLogicielle.routes.api.GetTasksListRoute;
import fr.esiee.usineLogicielle.routes.api.PostAddTaskRoute;
import fr.esiee.usineLogicielle.routes.api.PutTaskEditRoute;

/**
 * Test sur les routes du projet.
 * Utilisation de mock avec EasyMock
 * 
 * @author perdigao
 *
 */
public class SparkTest extends EasyMockSupport
{
	/**
	 * Variable permettant d'utiliser EasyMock.
	 */
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);
	
	/**
	 * Mock de la classe modele du back-end.
	 */
	@Mock
	private TasksService model;
	
	/**
	 * Mock de la requete HTTP
	 */
	@Mock
	private Request request;
	
	/**
	 * Mock de la reponse HTTP
	 */
	@Mock
	private Response response;
	
	/**
	 * Route de suppression d'une tache.
	 */
	@TestSubject
	private final DeleteTaskRoute deleteTaskRoute = new DeleteTaskRoute(model);
	
	/**
	 * Route de modification d'une tache.
	 */
	@TestSubject
	private final PutTaskEditRoute editTaskRoute = new PutTaskEditRoute(model);
	
	/**
	 * Route d'ajout d'une tache en memoire.
	 */
	@TestSubject
	private final PostAddTaskRoute addTaskRoute = new PostAddTaskRoute(model);
	
	/**
	 * Route de recuperation d'une tache precise en memoire.
	 */
	@TestSubject
	private final GetTaskViewRoute getTaskRoute = new GetTaskViewRoute(model);
	
	/**
	 * Route de recuperation de la liste de toutes les taches en memoire.
	 */
	@TestSubject
	private final GetTasksListRoute getTaskListRoute = new GetTasksListRoute(model);
	
	/**
	 * Test qui verifie que la route renvoie "ok" si la suppression s'est deroulee correctement.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteTaskRouteTest1() throws Exception
	{
		model.deleteTask(1);
		expectLastCall();
		expect(request.params(":id")).andReturn("1");
        response.type(anyString());
        expectLastCall();
        response.status(200);
        expectLastCall();
		replayAll();
		
		String res = (String)deleteTaskRoute.handle(request, response);

		assertEquals("ok", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie "erreur" si la suppression s'est mal passee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteTaskRouteTest2() throws Exception
	{
		model.deleteTask(1);
		expectLastCall().andThrow(new TasksServiceException(""));
		expect(request.params(":id")).andReturn("1");
        response.type(anyString());
        expectLastCall();
        response.status(500);
        expectLastCall();
		replayAll();
		
		String res = (String)deleteTaskRoute.handle(request, response);

		assertEquals("error", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie "ok" si la modification d'une tache s'est deroulee correctement.
	 * 
	 * @throws Exception
	 */
	@Test
	public void editTaskRouteTest1() throws Exception
	{
		expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		model.editTask(EasyMock.anyObject());
		expectLastCall();
        response.type(anyString());
        expectLastCall();
        response.status(200);
        expectLastCall();
		replayAll();
		
		String res = (String)editTaskRoute.handle(request, response);

		assertEquals("ok", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie "erreur" si la modification d'une tache s'est mal passee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void editTaskRouteTest2() throws Exception
	{
		expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		model.editTask(EasyMock.anyObject());
		expectLastCall().andThrow(new TasksServiceException(""));
        response.type(anyString());
        expectLastCall();
        response.status(500);
        expectLastCall();
		replayAll();
		
		String res = (String)editTaskRoute.handle(request, response);

		assertEquals("error", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie si les valeurs recuperees en entree sont bien en Json.
	 * Si ce n'est pas le cas, le programme renvoie "La tache envoyee est dans un format json incorrect"
	 * 
	 * @throws Exception
	 */
	@Test
	public void editTaskRouteTest3() throws Exception
	{
		expect(request.body()).andReturn("fake");
		model.editTask(EasyMock.anyObject());
		expectLastCall();
        response.type(anyString());
        expectLastCall();
        response.status(500);
        expectLastCall();
		replayAll();
		
		String res = (String)editTaskRoute.handle(request, response);

		assertEquals("Impossible d'editer la tâche: mauvais format JSON", res);
	}
		
	/**
	 * Test qui verifie que la route renvoie "ok" si l'ajoue d'une tache s'est deroulee correctement.
	 * 
	 * @throws Exception
	 */
	@Test
	public void addTaskRouteTest1() throws Exception
	{
		expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		model.addTask(EasyMock.anyObject());
		expectLastCall();
        response.type(anyString());
        expectLastCall();
        response.status(200);
        expectLastCall();
		replayAll();
		
		String res = (String)addTaskRoute.handle(request, response);

		assertEquals("ok", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie "erreur" si l'ajoue d'une tache s'est mal passee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void addTaskRouteTest2() throws Exception
	{
		expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		model.addTask(EasyMock.anyObject());
		expectLastCall().andThrow(new TasksServiceException(""));
        response.type(anyString());
        expectLastCall();
        response.status(500);
        expectLastCall();
		replayAll();
		
		String res = (String)addTaskRoute.handle(request, response);

		assertEquals("error", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie si les valeurs recuperees en entree sont bien en Json.
	 * Si ce n'est pas le cas, le programme renvoie "La tache envoyee est dans un format json incorrect"
	 * 
	 * @throws Exception
	 */
	@Test
	public void addTaskRouteTest3() throws Exception
	{
		expect(request.body()).andReturn("fake");
		model.addTask(EasyMock.anyObject());
		expectLastCall();
        response.type(anyString());
        expectLastCall();
        response.status(500);
        expectLastCall();
        replayAll();
		
		String res = (String)addTaskRoute.handle(request, response);

		assertEquals("La tache envoyee est dans un format json incorrect", res);
	}
	
	/**
	 * Test qui verifie que la route renvoie bien une tache avec les bonnes valeurs.
	 * @throws Exception
	 */
	@Test
	public void getTaskRouteTest1() throws Exception
	{
		Task t = new Task();
		t.setId(1);
		t.setTitle("titre");
		t.setBody("Ceci est un test");
		
		expect(request.params(":id")).andReturn("1");
		expect(model.getTaskByID(1)).andReturn(t);
        response.type(anyString());
        expectLastCall();
		replayAll();
		
		Task res = (Task)getTaskRoute.handle(request, response);

		assertEquals(1, res.getId());
		assertEquals("titre", res.getTitle());
		assertEquals("Ceci est un test", res.getBody());
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie "There is no task with id % found" si la tache n'a pas ete trouvee en memoire.
	 * @throws Exception
	 */
	@Test
	public void getTaskRouteTest2() throws Exception
	{	
		expect(request.params(":id")).andReturn("1");
		expect(model.getTaskByID(1)).andReturn(null);
        response.type(anyString());
        expectLastCall();
		replayAll();
		
		String res = (String)getTaskRoute.handle(request, response);

		assertEquals("There is no task with id 1 found", res);
		assertNotEquals("There is no task with id 2 found", res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie si l'id de la tache envoyee par l'utilisateur est bien un entier, sinon la route renvoie "l'identifiant n'est pas un entier".
	 * @throws Exception
	 */
	@Test
	public void getTaskRouteTest3() throws Exception
	{
		expect(request.params(":id")).andReturn("fake");
		expect(model.getTaskByID(1)).andReturn(null);
        response.type(anyString());
        expectLastCall();
		replayAll();
		
		String res = (String)getTaskRoute.handle(request, response);

		assertEquals("l'identifiant n'est pas un entier", res);
	}
	
	/**
	 * Test qui verifie que la route renvoie null si la liste des taches n'est pas initialisee.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTaskListRouteTest1() throws Exception
	{	
		expect(model.getTaskList()).andReturn(null);
        response.type(anyString());
        expectLastCall();
		replayAll();
		
		Object res = getTaskListRoute.handle(null, response);

		assertEquals(null, res);
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie une liste vide si la BDD ne contient pas de tache.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getTaskListRouteTest2() throws Exception
	{
		expect(model.getTaskList()).andReturn(new ArrayList<Task>());
		response.type(anyString());
		expectLastCall();
		replayAll();
		
		@SuppressWarnings("unchecked")
		List<Task> res = (List<Task>) getTaskListRoute.handle(null, response);

		assertEquals(0, res.size());
		
		verifyAll();
	}
	
	/**
	 * Test qui verifie que la route renvoie bien la liste de toutes les taches presentes en BDD.
	 * @throws Exception
	 */
	@Test
	public void getTaskListRouteTest3() throws Exception
	{
		List<Task> returnedByMock = new ArrayList<Task>();
		returnedByMock.add(new Task());
		returnedByMock.add(new Task());
		
		expect(model.getTaskList()).andReturn(returnedByMock);
        response.type(anyString());
        expectLastCall();
		replayAll();
		
		@SuppressWarnings("unchecked")
		List<Task> res = (List<Task>) getTaskListRoute.handle(null, response);

		assertEquals(2, res.size());
		
		verifyAll();
	}

}
