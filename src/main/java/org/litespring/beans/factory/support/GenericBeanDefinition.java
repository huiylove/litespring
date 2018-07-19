package org.litespring.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��11��
 * @version : 1.0
 */
public class GenericBeanDefinition implements BeanDefinition {

	private String id;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	
	private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
	
	private ConstructorArgument constructorArgument = new ConstructorArgument();
	
	public GenericBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanClassName() {
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

	@Override
	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	@Override
	public ConstructorArgument getConstructorArgument() {
		return constructorArgument;
	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public boolean hasConstructorArgumentValues() {
		return !this.constructorArgument.isEmpty();
	}
	
	

}
