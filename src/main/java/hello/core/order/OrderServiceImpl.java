package hello.core.order;

import com.sun.source.tree.UsesTree;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.dicount.DiscountPolicy;
import hello.core.dicount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor => final이 붙은 필수 값의 생성자 생성
public class OrderServiceImpl implements OrderService {
//  ⓐ주문 생성 요청이 오면,,
//  3.(AppConfig 이후..)private final MemberRepository memberRepository = new MemoryMemberRepository();
//  1.private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//  1.MemoryMemberRepository와 FixDiscountPolicy를 구현체로 생성하여 사용
//  2.private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
/*  잘 생각해 보면 클라이언트인 OrderServiceImpl이 DiscountPolicy 인터페이스
    뿐만 아니라 FixDiscountPolicy인 구체 클래스도 함께 의존하고 있다. => "DIP 위반"
    구체(클래스)에 의존하지말고 항상 추상(인터페이스)에 의존하라고 했는데 둘다 의존하고 있음.

    그래서 FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl
    의 소스코드도 함께 변경해야 한다! => "OCP 위반"
 */
//  3.(AppConfig 이후..)private DiscountPolicy discountPolicy;

    // * 필드 주입
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /*
    스프링 컨테이너는 크게 2가지 LifeCycle존재
    1. 스프링 빈을 등록하는 단계
    2. 연관관계를 자동으로 주입(@Autowired)하는 단계
     */

    /*
    @Autowired(required = false) // * 수정자 주입(setter 주입: 선택, 변경 의존관계)
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }
    */

    @Autowired // ★ * 생성자 주입(불변, 필수 의존관계) ★
    // ★ 생성자 주입을 선택해라! => 불변, 누락방지, final키워드
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        // System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        // System.out.println("1. memberRepository = " + memberRepository);
        // System.out.println("1. discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /*
    @Autowired // * 일반 메서드 주입
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    */

/*
    3.(AppConfig 이후..) OrderServiceImpl은 DIP를 잘 지킴.
    MemberRepository, DiscountPolicy와 같은 인터페이스(추상)에만 의존하고 있고,
    어떤 memberRepository가 들어올지, 어떤(Fix, Rate)discountPolicy가 (구체)
    들어올지에 대해서 OrderServiceImpl은 알지 못함.
 */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
//  ⓑ회원 정보를 먼저 조회를 하고,
        int discountPrice = discountPolicy.discount(member, itemPrice);
//  ⓒ그 다음 할인 정책에 회원을 넘겨서,
//  ⓓ최종적으로 할인된 가격을 받음.
        return new Order(memberId, itemName, itemPrice, discountPrice);
//  ⓔ그리고 최종 생성된 주문을 반환
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
