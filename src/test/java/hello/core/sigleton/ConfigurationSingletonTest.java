package hello.core.sigleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        /*
        AppConfig가 있는데 CGLIB라는 바이트코드조작 라이브러리를 가지고
        얘를 상속받아가지고 다른 클래스를 하나 만듦 그리고 스프링이 AppConfig
        말고 AppConfig@CGLIB라는 조작한 클래스를 스프링 빈으로 등록 함.

        그래서 스프링 컨테이너에는 이름은 appConfig인데, instance 객체가
        AppConfig@CGLIB인 얘가 들어가 있는 것이다...

        => 그 임의의 다른 클래스가 바로 싱글톤이 보장되도록 해준다.
         */

        /*
        @Configuration을 적용하지 않고, @Bean만 적용하면 어떻게 될까?

        => AppConfig가 CGLIB기술 없이 순수한 AppConfig로 스프링 빈에
        등록되고, MemberRepository가 1번이 아닌 총 3번 호출된다. 그리고
        각 MemberRepository의 인스턴스 값은 모두 다르다.

        => @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.
        memberRepository() 처럼 의존관계 주입이 필요해서 메서드를 직접 호출할
        때 싱글톤을 보장하지 않는다.
        Just, 스프링 설정 정보는 항상 @Configuration 을 사용하자
         */
    }
}
