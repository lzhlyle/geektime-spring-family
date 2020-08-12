package geektime.spring.web.context;

import geektime.spring.web.foo.FooConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class ContextHierarchyDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ContextHierarchyDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ApplicationContext fooAnnotationContext = new AnnotationConfigApplicationContext(FooConfig.class);
		ApplicationContext barXmlContext = new ClassPathXmlApplicationContext(
				new String[] {"applicationContext.xml"}, fooAnnotationContext); // (locations, parent)
		TestBean parentBean = fooAnnotationContext.getBean("testBeanX", TestBean.class);
		parentBean.hello();

		log.info("=============");

		TestBean subBean = barXmlContext.getBean("testBeanX", TestBean.class);
		subBean.hello();

		subBean = barXmlContext.getBean("testBeanY", TestBean.class); // sub(xml) 中没有，取 parent(annotation) 找
		subBean.hello();
	}
}
