package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class BeanCreationException extends BeansException{
	
	private String beanName;
	
	public BeanCreationException(String msg){
		super(msg);
	}
	
	public BeanCreationException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	public BeanCreationException(String beanName,String msg){
		super("Error create bean with name"+ beanName + ":"+ msg);
		this.beanName = beanName;
	}

	
	public BeanCreationException(String beanName,String msg,Throwable cause){
		this(beanName,msg);
		initCause(cause);
	}

	public String getBeanName() {
		return beanName;
	}

}
