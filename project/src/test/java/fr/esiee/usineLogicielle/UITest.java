package fr.esiee.usineLogicielle;

import static org.junit.Assert.*;

import org.junit.Test;

import spark.ModelAndView;
import fr.esiee.usineLogicielle.routes.ui.HomeRoute;
import fr.esiee.usineLogicielle.routes.ui.TaskCreateRoute;
import fr.esiee.usineLogicielle.routes.ui.TaskEditRoute;
import fr.esiee.usineLogicielle.routes.ui.TaskShowRoute;
import fr.esiee.usineLogicielle.routes.ui.TasksListRoute;


/**
 * Classe qui teste les routes de l'api cliente.
 * 
 * @author perdigao
 *
 */
public class UITest 
{
	/**
	 * Test de la creation de page Home.
	 * Regarde si le ModelAndView a ete cree correctement.
	 */
	@Test
	public void HomeRouteTest() 
	{
		HomeRoute route = new HomeRoute();
		ModelAndView mv;
		try {
			mv = (ModelAndView)route.handle(null, null);
			assertNotEquals(null, mv);
		} catch (Exception e) {
			assertEquals(1, 0);
		}
	}
	
	/**
	 * Test de la creation de la page de creation d'une tache.
	 * Regarde si le ModelAndView a ete cree correctement.
	 */
	@Test
	public void TaskCreateRouteTest() 
	{
		TaskCreateRoute route = new TaskCreateRoute();
		ModelAndView mv;
		try {
			mv = (ModelAndView)route.handle(null, null);
			assertNotEquals(null, mv);
		} catch (Exception e) {
			assertEquals(1, 0);
		}
	}
	
	/**
	 * Test de la creation de la page d'edition d'une tache.
	 * Regarde si le ModelAndView a ete cree correctement.
	 */
	@Test
	public void TaskEditRouteTest() 
	{
		TaskEditRoute route = new TaskEditRoute();
		ModelAndView mv;
		try {
			mv = (ModelAndView)route.handle(null, null);
			assertNotEquals(null, mv);
		} catch (Exception e) {
			assertEquals(1, 0);
		}
	}
	
	/**
	 * Test de la creation de la page de detail d'une tache.
	 * Regarde si le ModelAndView a ete cree correctement.
	 */
	@Test
	public void TaskShowRouteTest() 
	{
		TaskShowRoute route = new TaskShowRoute();
		ModelAndView mv;
		try {
			mv = (ModelAndView)route.handle(null, null);
			assertNotEquals(null, mv);
		} catch (Exception e) {
			assertEquals(1, 0);
		}
	}
	
	/**
	 * Test de la creation de la page de liste de toutes les taches.
	 * Regarde si le ModelAndView a ete cree correctement.
	 */
	@Test
	public void TaskListRouteTest() 
	{
		TasksListRoute route = new TasksListRoute();
		ModelAndView mv;
		try {
			mv = (ModelAndView)route.handle(null, null);
			assertNotEquals(null, mv);
		} catch (Exception e) {
			assertEquals(1, 0);
		}
	}
}
