package org.litespring.beans;
/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��11��
 * @version : 1.0
 */
public class BeansException extends RuntimeException{
	
	public BeansException(String msg){
		super(msg);
	}
	
	public BeansException(String msg,Throwable cause){
		super(msg,cause);
	}

}
