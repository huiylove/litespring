package org.litespring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018Äê6ÔÂ29ÈÕ
 * @version : 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({
	ApplicationContextTestV2.class,
	BeanDefinitionTestV2.class,
	BeanDefinitionValueResolverTest.class,
	CustomBooleanEditorTest.class,
	CustomNumberEditorTest.class,
	TypeConveterTest.class,
})
public class V2AllTests {

}
