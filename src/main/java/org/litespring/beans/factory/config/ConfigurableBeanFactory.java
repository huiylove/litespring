/**
 * created by yuanhui 2018Äê6ÔÂ21ÈÕ
 */
/**
 * @author user
 *
 */
package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {	
	void setBeanClassLoader(ClassLoader beanClassLoader);
	ClassLoader getBeanClassLoader();	
}
