package fr.esiee.usineLogicielle.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import spark.Request;
import spark.Response;
import spark.Spark;
import fr.esiee.usineLogicielle.Main;
import fr.esiee.usineLogicielle.Task;
import fr.esiee.usineLogicielle.TasksService;
import fr.esiee.usineLogicielle.Routes.DeleteTaskRoute;
import fr.esiee.usineLogicielle.Routes.GetTaskViewRoute;
import fr.esiee.usineLogicielle.Routes.GetTasksListRoute;
import fr.esiee.usineLogicielle.Routes.PostAddTaskRoute;
import fr.esiee.usineLogicielle.Routes.PutTaskEditRoute;

public class SparkTest extends EasyMockSupport
{
	@BeforeClass
	public static void beforeClass()
	{
		Main.main(null);
	}
	
	@AfterClass
	public static void afterClass()
	{
		Spark.stop();	
	}
	
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);
	
	@Mock
	private TasksService model;
	
	@Mock
	private Request request;
	
	@Mock
	private Response response;
	
	@TestSubject
	private final DeleteTaskRoute deleteTaskRoute = new DeleteTaskRoute(model);
	
	@TestSubject
	private final PutTaskEditRoute editTaskRoute = new PutTaskEditRoute(model);
	
	@TestSubject
	private final PostAddTaskRoute addTaskRoute = new PostAddTaskRoute(model);
	
	@TestSubject
	private final GetTaskViewRoute getTaskRoute = new GetTaskViewRoute(model);
	
	@TestSubject
	private final GetTasksListRoute getTaskListRoute = new GetTasksListRoute(model);
	
	@Test
	public void deleteTaskRouteTest1() throws Exception
	{
		EasyMock.expect(model.deleteTask(1)).andReturn((0));
		EasyMock.expect(request.params(":id")).andReturn("1");
		
		replayAll();
		
		String response = (String)deleteTaskRoute.handle(request, null);

		assertEquals("ok", response);
		
		verifyAll();
	}
	
	@Test
	public void deleteTaskRouteTest2() throws Exception
	{
		EasyMock.expect(model.deleteTask(1)).andReturn((-1));
		EasyMock.expect(request.params(":id")).andReturn("1");
		
		replayAll();
		
		String response = (String)deleteTaskRoute.handle(request, null);

		assertEquals("error", response);
		
		verifyAll();
	}
	
	@Test
	public void editTaskRouteTest1() throws Exception
	{
		EasyMock.expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		EasyMock.expect(model.editTask(EasyMock.anyObject())).andReturn(0);
		replayAll();
		
		String response = (String)editTaskRoute.handle(request, null);

		assertEquals("ok", response);
		
		verifyAll();
	}
	
	@Test
	public void editTaskRouteTest2() throws Exception
	{
		EasyMock.expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		EasyMock.expect(model.editTask(EasyMock.anyObject())).andReturn(-1);
		replayAll();
		
		String response = (String)editTaskRoute.handle(request, null);

		assertEquals("error", response);
		
		verifyAll();
	}
	
	@Test
	public void editTaskRouteTest3() throws Exception
	{
		EasyMock.expect(request.body()).andReturn("fake");
		EasyMock.expect(model.editTask(EasyMock.anyObject())).andReturn(0);
		replayAll();
		
		String response = (String)editTaskRoute.handle(request, null);

		assertEquals("La tache envoyée est dans un format json incorrect", response);
	}
		
	@Test
	public void addTaskRouteTest1() throws Exception
	{
		EasyMock.expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		EasyMock.expect(model.addTask(EasyMock.anyObject())).andReturn(0);
		replayAll();
		
		String response = (String)addTaskRoute.handle(request, null);

		assertEquals("ok", response);
		
		verifyAll();
	}
	
	@Test
	public void addTaskRouteTest2() throws Exception
	{
		EasyMock.expect(request.body()).andReturn("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		EasyMock.expect(model.addTask(EasyMock.anyObject())).andReturn(-1);
		replayAll();
		
		String response = (String)addTaskRoute.handle(request, null);

		assertEquals("error", response);
		
		verifyAll();
	}
	
	@Test
	public void addTaskRouteTest3() throws Exception
	{
		EasyMock.expect(request.body()).andReturn("fake");
		EasyMock.expect(model.addTask(EasyMock.anyObject())).andReturn(0);
		replayAll();
		
		String response = (String)addTaskRoute.handle(request, null);

		assertEquals("La tache envoyée est dans un format json incorrect", response);
	}
	
	@Test
	public void getTaskRouteTest1() throws Exception
	{
		Task t = new Task();
		t.id = 1;
		t.title = "titre";
		t.body = "Ceci est un test";
		
		EasyMock.expect(request.params(":id")).andReturn("1");
		EasyMock.expect(model.getTaskByID(1)).andReturn(t);
		replayAll();
		
		Task response = (Task)getTaskRoute.handle(request, null);

		assertEquals(1, response.id);
		assertEquals("titre", response.title);
		assertEquals("Ceci est un test", response.body);
		
		verifyAll();
	}
	
	@Test
	public void getTaskRouteTest2() throws Exception
	{	
		EasyMock.expect(request.params(":id")).andReturn("1");
		EasyMock.expect(model.getTaskByID(1)).andReturn(null);
		replayAll();
		
		String response = (String)getTaskRoute.handle(request, null);

		assertEquals("There is no task with id 1 found", response);
		assertNotEquals("There is no task with id 2 found", response);
		
		verifyAll();
	}
	
	@Test
	public void getTaskRouteTest3() throws Exception
	{
		EasyMock.expect(request.params(":id")).andReturn("fake");
		EasyMock.expect(model.getTaskByID(1)).andReturn(null);
		replayAll();
		
		String response = (String)getTaskRoute.handle(request, null);

		assertEquals("l'identifiant n'est pas un entier", response);
	}
	
	@Test
	public void getTaskListRouteTest1() throws Exception
	{	
		EasyMock.expect(model.getTaskList()).andReturn(null);
		replayAll();
		
		Object response = getTaskListRoute.handle(null, null);

		assertEquals(null, response);
		
		verifyAll();
	}
	
	@Test
	public void getTaskListRouteTest2() throws Exception
	{
		EasyMock.expect(model.getTaskList()).andReturn(new ArrayList<Task>());
		replayAll();
		
		List<Task> response = (List<Task>) getTaskListRoute.handle(null, null);

		assertEquals(0, response.size());
		
		verifyAll();
	}
	
	@Test
	public void getTaskListRouteTest3() throws Exception
	{
		List<Task> returnedByMock = new ArrayList<Task>();
		returnedByMock.add(new Task());
		returnedByMock.add(new Task());
		
		EasyMock.expect(model.getTaskList()).andReturn(returnedByMock);
		replayAll();
		
		List<Task> response = (List<Task>) getTaskListRoute.handle(null, null);

		assertEquals(2, response.size());
		
		verifyAll();
	}

}
