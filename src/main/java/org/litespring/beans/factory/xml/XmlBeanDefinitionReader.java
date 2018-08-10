package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;


/** 
 * 
 * @author : yuanhui 
 * @date   : 2018年6月21日
 * @version : 1.0
 */
public class XmlBeanDefinitionReader {
	
	 
	public static final String ID_ATTRIBUTE = "id";	

	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRIBUTE = "scope";
	
	public static final String PROPERTY_ELEMENT = "property";
	
	public static final String REF_ATTRIBUTE = "ref";
	
	public static final String VALUE_ATTRIBUTE = "value";
	
	public static final String NAME_ATTRIBUTE = "name";
	
	public static final String CONSTRUCTOR_ARG_ATTRIBUTE = "constructor-arg";
	
	public static final String TYPE_ATTRIBUTE = "type";
	
	public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

	public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
	
	private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
	
	BeanDefinitionRegistry registry;
	
	protected final Log logger = LogFactory.getLog(getClass());

	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}
	
	
	public void loadBeanDefinitions(Resource resource) {
		InputStream is = null;
		try {
//			ClassLoader cl = ClassUtils.getDefaultClassLoader();
//			is = cl.getResourceAsStream(configFile);
			
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc =  reader.read(is);
			
			Element root = doc.getRootElement();//<beans>标签
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				String namespaceUri = ele.getNamespaceURI();
				if(this.isDefaultNamespace(namespaceUri)){
					parseDefaultElement(ele); //普通的bean
				} else if(this.isContextNamespace(namespaceUri)){
					parseComponentElement(ele); //例如<context:component-scan>
				} 
				/**
				Element el = (Element) iter.next();
				String id = el.attributeValue(ID_ATTRIBUTE);
				String beanClassName = el.attributeValue(CLASS_ATTRIBUTE);
				String scope = el.attributeValue(SCOPE_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
				if(scope!=null){
					bd.setScope(scope);
				}
				parseConstructorArgElements(el,bd);
				parsePropertyElement(el,bd);
				this.registry.registerBeanDefinition(id, bd);
				**/
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
	
	private void parseComponentElement(Element ele) {
		String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		scanner.doScan(basePackages);		
	}
	
	
	private void parseDefaultElement(Element ele) {
		String id = ele.attributeValue(ID_ATTRIBUTE);
		String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
		BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
		if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {					
			bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));					
		}
		parseConstructorArgElements(ele,bd);
		parsePropertyElement(ele,bd); 
		this.registry.registerBeanDefinition(id, bd);
		
	}
	public boolean isDefaultNamespace(String namespaceUri) {
		return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
	}
	public boolean isContextNamespace(String namespaceUri){
		return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
	}
	
	
	public void parseConstructorArgElements(Element beanElem, BeanDefinition bd) {
		Iterator iter = beanElem.elementIterator(CONSTRUCTOR_ARG_ATTRIBUTE);
		while(iter.hasNext()){
			Element ele = (Element)iter.next();
			parseConstructorArgElement(ele,bd);
		}
	}
	
	public Object parseConstructorArgElement(Element ele,BeanDefinition bd){
		String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
		String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
		Object value = parsePropertyValue(ele, bd, null);
		ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
		if (StringUtils.hasLength(typeAttr)) {
			valueHolder.setType(typeAttr);
		}
		if (StringUtils.hasLength(nameAttr)) {
			valueHolder.setName(nameAttr);
		}
		bd.getConstructorArgument().addArgumentValue(valueHolder);	
		return null;
	}
	
	
	
	/**
	 * @param beanElem <property name="" value=""> <property name="" ref="">
	 * @param bd
	 */
	public void parsePropertyElement(Element beanElem, BeanDefinition bd) {
		Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);
		while(iter.hasNext()){
			Element propElem = (Element)iter.next();
			String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
			if (!StringUtils.hasLength(propertyName)) {
				logger.fatal("Tag 'property' must have a 'name' attribute");
				return;
			}
			
			Object val = parsePropertyValue(propElem, bd, propertyName);
			PropertyValue pv = new PropertyValue(propertyName, val);
			bd.getPropertyValues().add(pv);
		}
	}
	
	//bd参数有何用？？---后续有分支有判断
	//不在此处直接实例化ref引用的对象
	 //1、职责隔离原则  2、lazy load 延迟加载的思想，在getBean的时候去实例化
	public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
		String elementName = (propertyName != null) ?
						"<property> element for property '" + propertyName + "'" :
						"<constructor-arg> element";

		boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE)!=null);
		boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) !=null);
		
		if (hasRefAttribute) {
			String refName = ele.attributeValue(REF_ATTRIBUTE);
			if (!StringUtils.hasText(refName)) {
				logger.error(elementName + " contains empty 'ref' attribute");
			}
			RuntimeBeanReference ref = new RuntimeBeanReference(refName);			
			return ref;
		}else if (hasValueAttribute) {
			TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
			return valueHolder;
		}		
		else {
			throw new RuntimeException(elementName + " must specify a ref or value");
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
