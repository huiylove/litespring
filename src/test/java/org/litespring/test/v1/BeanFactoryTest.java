package org.litespring.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class BeanFactoryTest {
	
	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;
	
	@Before
	public void laodBeanDefintion(){
		factory = new DefaultBeanFactory();
	    reader = new XmlBeanDefinitionReader(factory);
	}

	public void testGetBean() {
		
//		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		
//		DefaultBeanFactory factory = new DefaultBeanFactory();
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//		reader.loadBeanDefiniton("petstore-v1.xml");
		
		reader.loadBeanDefiniton(new ClassPathResource("petstore-v1.xml"));

		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		assertTrue(bd.isSingleton());
		assertFalse(bd.isPrototype());
		
		assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());

		assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanNameClass());
		
		PetStoreService petService = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petService);
		
		PetStoreService petService1 = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petService1);
		
		assertEquals(petService,petService1);
		
		
	}
	
	@Test
	public void testGetBeanPrototype() {
		
		reader.loadBeanDefiniton(new ClassPathResource("petstore-v1.xml"));

		BeanDefinition bd = factory.getBeanDefinition("petStore");
		
		assertFalse(bd.isSingleton());
		assertTrue(bd.isPrototype());
		
		assertEquals(BeanDefinition.SCOPE_PROTOTYPE,bd.getScope());

		assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanNameClass());
		
		PetStoreService petService = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petService);
		
		PetStoreService petService1 = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petService1);
		
		assertNotEquals(petService,petService1);
		
	}
	
	@Test
	public void testInvalidBean() {
////		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		try {
			reader.loadBeanDefiniton("petstore-v1.xml");
			factory.getBean("invalidBean");
		} catch (BeanCreationException e) {
			return ;
		}
		Assert.fail("expect BeanCreationException");
	}

	
	@Test
	public void testInvalidXml() {
		try {
////			new DefaultBeanFactory("XXX.xml");		
////			DefaultBeanFactory factory = new DefaultBeanFactory();
////			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
////			reader.loadBeanDefiniton("XXX.xml");
			
			reader.loadBeanDefiniton("XXX.xml");

		} catch (BeanDefinitionStoreException e) {
			return ;
		}
		Assert.fail("expect BeanDefinitionStoreException");
	}

}
