package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��11��
 * @version : 1.0
 */
public interface BeanFactory {

	/**
	 * �ڲ�ʵ�֣�����Ҫ��¶���ͻ���
	 * @param beanID
	 * @return
	 */
//	@Deprecated
//	BeanDefinition getBeanDefinition(String beanID);

	Object getBean(String beanID);

}
