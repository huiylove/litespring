package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;


/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��21��
 * @version : 1.0
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);
	void registerBeanDefinition(String beanID, BeanDefinition bd);

}
