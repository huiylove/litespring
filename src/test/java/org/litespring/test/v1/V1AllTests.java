package org.litespring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018��6��21��
 * @version : 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({
	ApplicationContextTest.class,
	BeanFactoryTest.class,
	ResourceTest.class

})
public class V1AllTests {

}
