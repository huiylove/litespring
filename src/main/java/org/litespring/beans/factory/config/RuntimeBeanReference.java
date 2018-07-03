package org.litespring.beans.factory.config;
/** 
 * 
 * @author : yuanhui 
 * @date   : 2018年6月28日
 * @version : 1.0
 */
public class RuntimeBeanReference {
	
	private final String beanName;//beanName变成实例   beanID变成一个实例
	
	public RuntimeBeanReference(String beanName) {
		this.beanName = beanName;
	}
	
	public String getBeanName() {
		return this.beanName;
	}
}
