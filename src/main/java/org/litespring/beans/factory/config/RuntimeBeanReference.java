package org.litespring.beans.factory.config;
/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��28��
 * @version : 1.0
 */
public class RuntimeBeanReference {
	
	private final String beanName;//beanName���ʵ��   beanID���һ��ʵ��
	
	public RuntimeBeanReference(String beanName) {
		this.beanName = beanName;
	}
	
	public String getBeanName() {
		return this.beanName;
	}
}
