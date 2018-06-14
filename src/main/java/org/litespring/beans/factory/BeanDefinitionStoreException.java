package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class BeanDefinitionStoreException extends BeansException{
	
	
	public BeanDefinitionStoreException(String msg){
		super(msg);
	}
	
	public BeanDefinitionStoreException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	public BeanDefinitionStoreException(String configFile,String msg){
		super("Error parsing xml"+ configFile + ":"+ msg);
	}

	
	public BeanDefinitionStoreException(String configFile,String msg,Throwable cause){
		this(configFile,msg);
		initCause(cause);
	}

}
