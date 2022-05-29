package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    // AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    // => getBeanDefinition을 쓰려면 ApplicationContext로는 안되고, AnnotationConfigApplicationContext을 써야 함

    // java아닌 xml appConfig로 해본다면?
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
    // (굳이 알 필욘 없지만..) 차이는? xml은 직접 스프링빈 등록, java는 factoryBean을 등록함

    @Test
    @DisplayName("빈 설정 메타정보 확인 = beanDefinition 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}
