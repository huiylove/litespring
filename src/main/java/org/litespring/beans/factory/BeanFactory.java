package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public interface BeanFactory {

	BeanDefinition getBeanDefinition(String beanID);

	Object getBean(String beanID);

}
