package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//      basePackages = "hello.core.member",
//      탐색할 패키지의 시작 위치를 지정
//      basePackageClasses = AutoAppConfig.class,
//      지정한 클래스의 패키지를 탐색 시작 위치로 지정
/*      ** default **  만약 지정하지 않으면
        @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        => 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두도록 하자!
 */
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만,
        // 기존 예제 코드를 유지하기 위해서 이 방법을 선택.
)

public class AutoAppConfig {
/*
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
*/
    /*
    이 경우 수동 빈 등록이 우선권을 가진다.
    => 수동 빈이 자동 빈을 오버라이딩 해버림.
     */
}


