/**
 * created by yuanhui 2018��6��21��
 */
/**
 * @author user
 *
 */
package org.litespring.beans.factory.config;

import java.util.List;


public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {	
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
	List<BeanPostProcessor> getBeanPostProcessors();
}
