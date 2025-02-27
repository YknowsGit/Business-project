package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{
//  MemberServiceImpl은 MemberRepository(추상화) 에도 의존하고, MemoryMemberRepository(구체화)에도 의존
//  => DIP 위반!
//  private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;
/*  MemberServiceImpl에 MemoryMemberRepository(구체화)에 대한 코드가 없음.
    오로지 MemberRepository(인터페이스)에 대한 것만 있음, 추상화에만 의존
    => DIP를 잘 지킴.
 */
    @Autowired
    /*
    ac.getBean(MemberRepository.class)
       ComponentScan을 쓰면 Autowired를 쓰게 됨
       Why? 내 Bean이 자동으로 등록은 되는데 의존 관계를 설정할 수 있는 방법이 없음
       수동으로 등록할 수 있는 장소가 없기 때문...
       => @Autowired는 의존관계를 자동으로 주입해준다.
     */
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
