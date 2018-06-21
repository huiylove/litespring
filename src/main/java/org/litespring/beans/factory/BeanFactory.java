package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018年6月11日
 * @version : 1.0
 */
public interface BeanFactory {

	/**
	 * 内部实现，不需要暴露给客户端
	 * @param beanID
	 * @return
	 */
//	@Deprecated
//	BeanDefinition getBeanDefinition(String beanID);

	Object getBean(String beanID);

}
