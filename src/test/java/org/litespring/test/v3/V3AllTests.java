package org.litespring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê7ÔÂ3ÈÕ
 * @version : 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({ ApplicationContextTestV3.class, BeanDefinitionTestV3.class,
		ConstructorResolverTest.class })
public class V3AllTests {

}
