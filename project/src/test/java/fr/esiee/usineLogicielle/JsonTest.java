package fr.esiee.usineLogicielle;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.esiee.usineLogicielle.JsonUtil;
import fr.esiee.usineLogicielle.Task;

/**
 * Test sur la classe JsonUtil
 * 
 * @author perdigao
 *
 */
public class JsonTest
{

	/**
	 * test si l'appli peut récupérer une tache codée en Json.
	 */
	@Test
	public void fromJsonTest() 
	{
		Task t = JsonUtil.fromJson("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}");
		
		assertEquals(1, t.getId());
		assertEquals("Test1", t.getTitle());
		assertEquals("Ceci est un test1", t.getBody());
	}
	
	/**
	 * test si l'appli peut décoder un objet en format Json.
	 */
	@Test
	public void toJsonTest() 
	{
		Task t = new Task();
		t.setId(1);
		t.setTitle("Test1");
		t.setBody("Ceci est un test1");
		
		String json = JsonUtil.toJson(t);
		
		assertEquals("{\"id\":1,\"title\":\"Test1\",\"body\":\"Ceci est un test1\"}", json);
	}

}
