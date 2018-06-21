package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;


/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ21ÈÕ
 * @version : 1.0
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);
	void registerBeanDefinition(String beanID, BeanDefinition bd);

}
