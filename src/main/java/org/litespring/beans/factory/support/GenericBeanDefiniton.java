package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class GenericBeanDefiniton implements BeanDefinition {

	private String id;
	private String beanClassName;
	
	
	public GenericBeanDefiniton(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanNameClass() {
		return beanClassName;
	}

}
