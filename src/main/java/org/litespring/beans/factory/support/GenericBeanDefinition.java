package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class GenericBeanDefinition implements BeanDefinition {

	private String id;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	
	
	public GenericBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanNameClass() {
		return beanClassName;
	}

	@Override
	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}

	@Override
	public boolean isSingleton() {
		return this.singleton;
	}

	@Override
	public boolean isPrototype() {
		return this.prototype;
	}

	@Override
	public String getScope() {
		return this.scope;
	}

}
