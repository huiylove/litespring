package org.litespring.test.v1;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

/** 
 * 
 * @author : yuanhui 
 * @date   : 2018年6月21日
 * @version : 1.0
 */
public class ResourceTest {
	
	@Test
	public void testClassPathResource() throws IOException{
		Resource r = new ClassPathResource("petstore-v1.xml");

		InputStream is = null;

		try {
			is = r.getInputStream();
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		
	}
	
	@Test
	public void testFileSystemResource() throws IOException{
		//相对路径
		Resource r = new FileSystemResource("src\\test\\resources\\petstore-v1.xml");

		InputStream is = null;

		try {
			is = r.getInputStream();
			// 注意：这个测试其实并不充分！！
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		
	}

}
