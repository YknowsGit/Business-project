package hello.core;

import hello.core.member.Grade;
import hello.core.member.MemberService;
import hello.core.member.Member;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

//      2.AppConfig appConfig = new AppConfig();
//      2.MemberService memberService = appConfig.memberService();
//      2.OrderService orderService = appConfig.orderService();

//      1.MemberServiceImpl memberService = new MemberServiceImpl();
//      1.OrderServiceImpl orderService = new OrderServiceImpl();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
//      System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
