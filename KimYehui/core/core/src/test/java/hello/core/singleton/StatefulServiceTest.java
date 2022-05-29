package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //A가 주문 후, 주문금액 조회하기 직전에 B가 주문해버렸다면, A가 조회한 주문금액정보는??
        //=> stateful한 상황에서는, B가 주문시 저장되어 20000원 조회되어버림
        //ThreadA : A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB : B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA : 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        // 즉, price는 공유되는 필드인데, 특정 클라이언트가 값을 변경해버린다
        // 공유필드를 조심하고 무상태로 설계해라!
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}