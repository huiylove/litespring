package org.litespring.beans.factory.support;
/** 
 * 
 * @author : yuanhui 
 * @date   : 2018年7月26日
 * @version : 1.0
 */
public class SoftReference<T> {
	
	private T cl;
	
	public SoftReference(T cl){
		this.cl =  cl;
		
	}
	
	public T get(){
		return this.cl;
	}

}
