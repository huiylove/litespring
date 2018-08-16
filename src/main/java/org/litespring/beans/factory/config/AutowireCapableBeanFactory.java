package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê8ÔÂ16ÈÕ
 * @version : 1.0
 */
public interface AutowireCapableBeanFactory extends BeanFactory{
	
	public Object resolveDependency(DependencyDescriptor descriptor);


}
