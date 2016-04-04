package fr.esiee.usineLogicielle.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Task;

public class JsonTests {

	@Test
	public void fromJsonTest() 
	{
		Task t = JsonUtil.fromJson("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		
		assertEquals(1, t.id);
		assertEquals("Test1", t.title);
		assertEquals("Ceci est un test1", t.body);
	}
	
	@Test
	public void toJsonTest() 
	{
		Task t = new Task();
		t.id = 1;
		t.title = "Test1";
		t.body = "Ceci est un test1";
		
		String json = JsonUtil.toJson(t);
		
		assertEquals("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}", json);
	}

}
