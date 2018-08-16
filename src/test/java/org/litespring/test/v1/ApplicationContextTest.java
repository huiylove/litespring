package org.litespring.test.v1;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018年6月21日
 * @version : 1.0
 */
public class ApplicationContextTest {

	@Test
	public void testClassPathGetBean() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v1.xml");
//		context.setBeanClassLoader(null);//可以传classloader
		PetStoreService petService = (PetStoreService)context.getBean("petStore");
		assertNotNull(petService);
	}
	
	@Test
	public void testFileSystemGetBean() {
		
//		ApplicationContext context = new FileSystemXmlApplicationContext("C:\\Users\\user\\Desktop\\码农翻身\\petstore-v1.xml");
		ApplicationContext context = new FileSystemXmlApplicationContext("src\\test\\resources\\petstore-v1.xml");//相对路径

		PetStoreService petService = (PetStoreService)context.getBean("petStore");
		assertNotNull(petService);
	}
}
