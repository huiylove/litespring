package org.litespring.test.v4;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.service.v4.PetStoreService;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��8��16��
 * @version : 1.0
 */
public class DependencyDescriptorTest {

	@Test
	public void test() throws Exception {
		 
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petstore-v4.xml");
		reader.loadBeanDefinitions(resource);
		
		Field f = PetStoreService.class.getDeclaredField("accountDao");//����ȡField����
		DependencyDescriptor  descriptor = new DependencyDescriptor(f,true);
		Object o = factory.resolveDependency(descriptor);
		Assert.assertTrue(o instanceof AccountDao); 
	}

}
