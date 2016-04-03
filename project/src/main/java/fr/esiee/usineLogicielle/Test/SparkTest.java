package fr.esiee.usineLogicielle.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.utils.SparkUtils;
import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Main;
import fr.esiee.usineLogicielle.TasksService;
import fr.esiee.usineLogicielle.Routes.DeleteTaskRoute;

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
	
	@TestSubject
	private final DeleteTaskRoute deleteTaskRoute = new DeleteTaskRoute(model);
	
	private String connectToService(Route route, String urlServer, String urlRoute, String connectionMethod) throws Exception
	{
		Spark.delete(urlRoute, route, JsonUtil.json());
		
		URL obj = new URL(urlServer);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod(connectionMethod);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
	
	@Test
	public void deleteTaskRouteTest1() throws Exception
	{
		EasyMock.expect(model.deleteTask(1)).andReturn((0));
		replayAll();
		
		String response = connectToService(deleteTaskRoute, "http://127.0.0.1:4567/api/task-delete-fake/1", "/api/task-delete-fake/:id", "DELETE");

		Assert.assertEquals("\"ok\"", response);
		
		verifyAll();
	}
	
	@Test
	public void deleteTaskRouteTest2() throws Exception
	{
		EasyMock.expect(model.deleteTask(1)).andReturn((1));
		replayAll();
		
		String response = connectToService(deleteTaskRoute, "http://127.0.0.1:4567/api/task-delete-fake2/1", "/api/task-delete-fake2/:id", "DELETE");
		
		Assert.assertEquals("\"error\"", response);	
		
		verifyAll();
	}

}
