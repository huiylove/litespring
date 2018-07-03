package org.litespring.beans.factory.support;

import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��29��
 * @version : 1.0
 */
public class BeanDefinitionValueResolver {
	
	private final BeanFactory beanFactory;//使用接口，而非DefaultBeanFactory
	
	public BeanDefinitionValueResolver(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public Object resolveValueIfNecessary(Object value) { 
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;			
			String refName = ref.getBeanName();			
			Object bean = this.beanFactory.getBean(refName);				
			return bean;
		}else if (value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		} else{
			//TODO
			throw new RuntimeException("the value " + value +" has not implemented");
		}		
	}
	
	public Object resolveValueIfNecessary(PropertyValue pv,Object value) { 
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;			
			String refName = ref.getBeanName();			
			Object bean = this.beanFactory.getBean(refName);
			pv.setConvertedValue(bean);
			return bean;
		}else if (value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		} else{
			//TODO
			throw new RuntimeException("the value " + value +" has not implemented");
		}		
	}
}
