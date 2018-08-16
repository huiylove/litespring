package org.litespring.beans;

import java.util.List;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public interface BeanDefinition {
	
	public static final String SCOPE_DEFAULT = "";
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTOTYPE = "prototype";
	
	void setScope(String scope);

	boolean isSingleton();

	boolean isPrototype();

	public String getScope();
	
	public List<PropertyValue> getPropertyValues();
	
	public ConstructorArgument getConstructorArgument();

	public String getBeanClassName();
	
	public String getID();
	
	public boolean hasConstructorArgumentValues();
	
//	public void SetBeanClass(Class<?> cl);
//	
//	public Class<?> getBeanClass();

	boolean hasBeanClass();

	Class<?> resolveBeanClass(ClassLoader beanClassLoader) throws ClassNotFoundException;

	Class<?> getBeanClass() throws IllegalStateException;
	
}
