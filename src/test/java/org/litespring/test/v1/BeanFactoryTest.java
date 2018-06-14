package org.litespring.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class BeanFactoryTest {

	@Test
	public void testGetBean() {
		
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanNameClass());
		
		PetStoreService petService = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petService);
	}
	
	@Test
	public void testInvalidBean() {
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		try {
			factory.getBean("invalidBean");
		} catch (BeanCreationException e) {
			return ;
		}
		Assert.fail("expect BeanCreationException");
	}

	
	@Test
	public void testInvalidXml() {
		try {
			new DefaultBeanFactory("XXX.xml");		
		} catch (BeanDefinitionStoreException e) {
			return ;
		}
		Assert.fail("expect BeanDefinitionStoreException");
	}

}
