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

	String getScope();
	
	List<PropertyValue> getPropertyValues();

	String getBeanClassName();

}
