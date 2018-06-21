package org.litespring.beans;
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

	String getBeanNameClass();

	void setScope(String scope);

	boolean isSingleton();

	boolean isPrototype();

	String getScope();
	

}
