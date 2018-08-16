/**
 * created by yuanhui 2018Äê6ÔÂ21ÈÕ
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
