package org.litespring.context.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ21ÈÕ
 * @version : 1.0
 */
public class ClassPathXmlApplicationContext  extends AbstractApplicationContext{

	public ClassPathXmlApplicationContext(String path) {
		super(path);
	}

	@Override
	public Resource getResourceByPath(String path) {
		return new ClassPathResource(path,this.getBeanClassLoader());
	}


}
