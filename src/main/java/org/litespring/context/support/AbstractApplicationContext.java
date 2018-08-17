package org.litespring.context.support;

import org.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ21ÈÕ
 * @version : 1.0
 */
public abstract class AbstractApplicationContext implements ApplicationContext{
	
	
	private DefaultBeanFactory  factory = null;
	private ClassLoader beanClassLoader ;

	public AbstractApplicationContext(String path) {
//		factory = new DefaultBeanFactory();
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
////		reader.loadBeanDefiniton(configFile);
//		reader.loadBeanDefiniton(getResourceByPath(path));
//		factory.setBeanClassLoader(this.getBeanClassLoader());
		this(path,ClassUtils.getDefaultClassLoader());
	}
	
	public AbstractApplicationContext(String path,ClassLoader classloader) {
		factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
//		reader.loadBeanDefiniton(configFile);
		reader.loadBeanDefinitions(getResourceByPath(path));
//		this.beanClassLoader = classloader;
		factory.setBeanClassLoader(classloader);
		registerBeanPostProcessors(factory);//×¢²áBeanPostProcessors

	}
	
	
	@Override
	public Object getBean(String beanID) {
		return factory.getBean(beanID);
	}
	
//	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
	   this.beanClassLoader = 	beanClassLoader;
	}

//	@Override
	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null) ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
	}
	
	  
	protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory){
		AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
		postProcessor.setBeanFactory(beanFactory);
		beanFactory.addBeanPostProcessor(postProcessor);
	}
	
	
	public abstract Resource getResourceByPath(String path);

}
