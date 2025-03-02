package hello.core.web;

import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
//        MyLogger myLogger = myLoggerProvider.getObject();
        String requestURL = request.getRequestURL().toString();

        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
/*
오류발생

스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만,
request 스코프 빈은 아직 생성되지 않는다. 이 빈은 실제 고객의 요청이 와야생성할 수 있다!

해결방안
1. ObjectProvider 사용
=> ObjectProvider.getObject()를 호출하는 시점까지 request scope 빈의
생성을 지연할 수 있다.(스프링 컨테이너한테 요청하는 것을 지연할 수 있다.)
=>

2. 프록시 방식 사용
=> CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
=> 가짜 프록시 객체는 요청이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.

특징정리
=> 사실 Provider를 사용하든, 프록시를 사용하든 핵심 아이디어는
진짜 객체 조회를 꼭 필요한 시점까지 "지연처리 한다는 점"이다
 */