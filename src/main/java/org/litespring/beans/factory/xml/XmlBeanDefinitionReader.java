package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;


/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ21ÈÕ
 * @version : 1.0
 */
public class XmlBeanDefinitionReader {
	
	 
	public static final String ID_ATTRIBUTE = "id";	

	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRIBUTE = "scope";
	
	BeanDefinitionRegistry registry;
	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}
	
	
	public void loadBeanDefiniton(Resource resource) {
		InputStream is = null;
		try {
//			ClassLoader cl = ClassUtils.getDefaultClassLoader();
//			is = cl.getResourceAsStream(configFile);
			
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc =  reader.read(is);
			
			Element root = doc.getRootElement();
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element el = (Element) iter.next();
				String id = el.attributeValue(ID_ATTRIBUTE);
				String beanClassName = el.attributeValue(CLASS_ATTRIBUTE);
				String scope = el.attributeValue(SCOPE_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				if(scope!=null){
					bd.setScope(scope);
				}
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException("IOException parseing XML doucument "+resource.getDescription()+" fail ");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@Deprecated
	public void loadBeanDefiniton(String configFile) {
		InputStream is = null;
		try {
			ClassLoader cl = ClassUtils.getDefaultClassLoader();
			is = cl.getResourceAsStream(configFile);
			
			SAXReader reader = new SAXReader();
			Document doc =  reader.read(is);
			
			Element root = doc.getRootElement();
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element el = (Element) iter.next();
				String id = el.attributeValue(ID_ATTRIBUTE);
				String beanClassName = el.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				this.registry.registerBeanDefinition(id, bd);
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException("IOException parseing XML doucument "+configFile+" fail ");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	

}
