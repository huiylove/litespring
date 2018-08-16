package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.util.ClassUtils;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��11��
 * @version : 1.0
 */
public class DefaultBeanFactory  extends DefaultSingletonBeanRegistry
implements BeanDefinitionRegistry,ConfigurableBeanFactory{
	
	private static final String ID_ATTRIBUTE = "id";
	private static final String CLASS_ATTRIBUTE = "class";
	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>(64);
	private ClassLoader beanClassLoader;
	
	@Override
	public void registerBeanDefinition(String beanID, BeanDefinition bd) {
		this.beanDefinitionMap.put(beanID,bd);
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
		if(bd.isSingleton()){
			Object bean =  this.getSingleton(beanID);
			if(bean==null){
				bean =  this.createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		} 
		return createBean(bd);
	}
	
	public Object createBean(BeanDefinition bd){
		
		//实例化Bean
		Object bean = this.instantiateBean(bd);//constructor.newInstance()
		
		//装配Bean
		populateBean(bd,bean);
		
		return bean;
	}
	
	
	public void populateBean(BeanDefinition bd,Object bean){
		
		List<PropertyValue> pvs = bd.getPropertyValues();
		
		if (pvs == null || pvs.isEmpty()) {
			return;
		}
		
		try {
			
			BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			TypeConverter typeConvertor = new SimpleTypeConverter();
			
			for(PropertyValue pv : pvs){
				String propertyName = pv.getName();
				Object originalValue = pv.getValue();
				Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);
				
				for(PropertyDescriptor pd:pds){
					if(pd.getName().equals(propertyName)){
						//反射 setter注入
						pd.getWriteMethod().invoke(bean,typeConvertor.convertIfNecessary(resolvedValue,pd.getPropertyType()));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", e);
		} 
		
	}
	
	
	public Object instantiateBean(BeanDefinition bd){
		if(bd.hasConstructorArgumentValues()){
			ConstructorResolver resolver = new ConstructorResolver(this);
			return resolver.autowireConstructor(bd);
		}
		else{
			ClassLoader cl = this.getBeanClassLoader();//ClassUtils.getDefaultClassLoader();
			String beanClassName = bd.getBeanClassName();
			try {
				//为什么不用Class.forName(); Class.forName()初始化参数指定的类，执行静态代码
				Class<?> clz = cl.loadClass(beanClassName);
				return clz.newInstance();
				//beanClass缓存实现
//				Class<?> cacheBeanClass =  bd.getBeanClass();
//				Class<?> beanClass;
//				if(cacheBeanClass==null){
//					beanClass = cl.loadClass(beanClassName);
//					//添加到BeanDefinition  
//					bd.SetBeanClass(beanClass);
//				}else{
//					beanClass = cacheBeanClass;
//				}
//				return beanClass.newInstance();
			} catch (Exception e) {
				throw new BeanCreationException("Create bean for "+ beanClassName + "fail");
			}
		}
		
	}
	
	@Override
	public void setBeanClassLoader(ClassLoader beanClassLoader) {
	   this.beanClassLoader = 	beanClassLoader;
	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null) ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
	}
	
	
	@Override
	public Object resolveDependency(DependencyDescriptor descriptor) {
		Class<?> typeToMatch = descriptor.getDependencyType();
		for(BeanDefinition bd: this.beanDefinitionMap.values()){		
			//确保BeanDefinition 有Class对象  ????????
			resolveBeanClass(bd);//在getBeanClass之前
			Class<?> beanClass = bd.getBeanClass();			
			if(typeToMatch.isAssignableFrom(beanClass)){
				return this.getBean(bd.getID());
			}
		}
		return null;
	}
	
	public void resolveBeanClass(BeanDefinition bd) {
		if(bd.hasBeanClass()){
			return;
		} else{
			try {
				bd.resolveBeanClass(this.getBeanClassLoader());
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("can't load class:"+bd.getBeanClassName());
			}
		}
	}
	
	
	public DefaultBeanFactory() {
	}
	
	@Deprecated
	public DefaultBeanFactory(String configFile) {
		loadBeanDefiniton(configFile);
	}

	@Deprecated
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
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
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

}
