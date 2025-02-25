package hello.core;

import hello.core.dicount.DiscountPolicy;
import hello.core.dicount.FixDiscountPolicy;
import hello.core.dicount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()

    // * 우리의 의도
    // call AppConfig.memberService
    // call AppConfig.memberRepository 1 호출
    // call AppConfig.memberRepository 2 호출
    // call AppConfig.orderService
    // call AppConfig.memberRepository 3 호출 될 것이라고 생각

    // * 실제 출력 => 스프링이 어떤 방법을 써서 싱글톤을 보장해 주는구나!
    // call AppConfig.memberService
    // call AppConfig.memberRepository 실제로는 1번 호출 됨.
    // call AppConfig.orderService
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
//      생성자를 톻해서 객체가 들어간다고 하여, "생성자 주입" 이라고 한다.
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
/*
      "리팩터링"함 으로써 new MemoryMemberRepository() 이 부분이 중복 제거
      되었다. 이제 MemoryMemberRepository()를 다른 구현체로 변경할 때 한 부분만
      변경하면 된다.
 */

    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");;
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//      return new FixDiscountPolicy();
/*
        "리팩터링"함 으로써 AppConfig에 역할과 구현 클래스가 한눈에 들어온다.
        애플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있다.
 */
        return new RateDiscountPolicy();
/*
        Fix -> Rate 로 변경
        이제 할인 정책을 변경해도, AppConfig만 변경하면 된다. 클라이언트 코드인
        OrderServiceImpl을 포함한 "사용영역"의 어떤 코드도 변경할 필요X
        "구성영역"은 당연히 변경 된다. 구성 역할을 담당하는 AppConfig를
        애플리케이션이라는 공연의 기획자로 생각하자! 공연 기획자는 공연 참여자인
        구현 객체들을 모두 알아야 한다.
 */
    }

}
