/**
 * created by yuanhui 2018��6��21��
 */
/**
 * @author user
 *
 */
package org.litespring.beans.factory.config;


public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {	
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
}
