package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
        // => applicationContect를 닫아야하기 때문에 필요 ConfigurableAC
    }

    @Configuration
    static class LifeCycleConfig {

        // @Bean(initMethod = "init", destroyMethod = "close")
        /* destroyMethod의 특별한 기능
        기본값으로 (inferred) 추론 기능 등록 close, shutdown 라는 이름의
        메서드를 자동으로 호출해준다. 이름 그대로 종료 메서드를 추론해서 호출해준다.
        따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다.
        */
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}

/*
스프링 빈은 간단하게 다음과 같은 LifeCycle을 가진다.
1. 객체 생성 => 2. 의존관계 주입

cf.) 생성자 주입은 예외 => 객체를 만들때 이미 스프링 빈이 파라미터에
같이 들어와야 하기 때문

스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야
필요한 데이터를 사용할 수 있는 준비가 완료된다.

? 그렇다면  개발자가 의존관계 주입이 모두 완료된 시점을 어떻게 알 수 있을까?
=> 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화
시점을 알려주는 다양한 기능을 제공한다.
또한, 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다. 따라서
안전하게 종료 작업을 진행할 수 있다.

스프링 빈의 이벤트 라이프사이클(싱글 톤에 대한 예시)

1. 스프링 컨테이너 생성 => 2. 스프링 빈 생성 => 3. 의존관계 주입 =>
4. 초기화 콜백사용 => 5. 소멸전 콜백 => 6. 스프링 종료

초기화 콜백: 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
소멸전 콜백: 빈이 소멸되기 직전에 호출

스프링은 3가지 방법으로 빈 생명주기 콜백을 지원
1. 인터페이스(InitializingBean, DisposableBean)
2. 설정 정보에 초기화 메서드, 종료 메서드 지정
3. @PostConstruct, @PreDestroy 애노테이션 지원

1. 초기화, 소멸 인터페이스 단점

이 인터페이스는 스프링 전용 인터페이스로, 해당 코드가 스프링 전용 인터페이스에
의존. 초기화, 소멸 메서드의 이름을 변경할 수 없다. 내가 코드를 고칠 수 없는
외부 라이브러리에 적용할 수 없다.
=>  스프링 초창기에 나온 방법으로, 거의 사용 X

2. 설정 정보 사용 특징

메서드 이름을 자유롭게 줄 수 있다. 스프링 빈이 스프링 코드에 의존하지 않는다.
코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도
초기화, 종료 메서드를 적용할 수 있다.

3. @PostConstruct, @PreDestroy 애노테이션 특징

최신 스프링에서 가장 권장하는 방법이다.
애노테이션 하나만 붙이면 되므로 매우 편리하다.
컴포넌트 스캔과 잘 어울린다.
유일한 단점으로 외부 라이브러리에는 적용하지 못한다는 것이다.

정리
@PostConstruct, @PreDestroy 애노테이션을 사용하자

 */