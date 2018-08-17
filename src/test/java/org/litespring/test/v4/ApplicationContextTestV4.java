package org.litespring.test.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ21ÈÕ
 * @version : 1.0
 */
public class ApplicationContextTestV4 {

	@Test
	public void testGetBeanProperty() {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		
		assertNotNull(petStore.getAccountDao());
		assertNotNull(petStore.getItemDao());
		
		assertTrue(petStore.getAccountDao() instanceof AccountDao);
		assertTrue(petStore.getItemDao() instanceof ItemDao);
		
	}
	
}
