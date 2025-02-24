package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class BeanDefinitionTest {

//    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
// => AnoontationConfig를 쓰면 factoryBean을 통해서 등록이 되는 방식으로 스프링에게 제공된다.
// => 일반적으로 JavaConfig를 쓰는 방식과 동일
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}
