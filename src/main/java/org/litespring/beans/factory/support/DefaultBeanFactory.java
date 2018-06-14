package org.litespring.beans.factory.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ11ÈÕ
 * @version : 1.0
 */
public class DefaultBeanFactory implements BeanFactory {
	
	private static final String ID_ATTRIBUTE = "id";
	private static final String CLASS_ATTRIBUTE = "class";
	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>(64);


	public DefaultBeanFactory(String configFile) {
		loadBeanDefiniton(configFile);
	}

	private void loadBeanDefiniton(String configFile) {
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
				BeanDefinition bd = new GenericBeanDefiniton(id,beanClassName);
				this.beanDefinitionMap.put(id,bd);
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

	@Override
	public BeanDefinition getBeanDefinition(String beanID) {
		return this.beanDefinitionMap.get(beanID);
	}

	
	@Override
	public Object getBean(String beanID) {
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if(bd==null)
			throw new BeanCreationException("BeanDefiniton does not exist");
		ClassLoader cl = ClassUtils.getDefaultClassLoader();
		String beanClassName = bd.getBeanNameClass();
		try {
			Class<?> clz = cl.loadClass(beanClassName);
			return clz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("Create bean for "+ beanClassName + "fail");
		}
	}

}
