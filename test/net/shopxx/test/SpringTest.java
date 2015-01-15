package net.shopxx.test;

import net.shopxx.service.TestEntityService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	public static ApplicationContext context;
	@Before
	public void startSpring(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	@Test
	public void testSpringL() throws Exception{
		TestEntityService testEntityService = (TestEntityService) context.getBean("testEntityServiceImpl");
		System.out.println(testEntityService.count());
	}
	
}
