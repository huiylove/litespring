/**
 * created by yuanhui 2018Äê6ÔÂ21ÈÕ
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
