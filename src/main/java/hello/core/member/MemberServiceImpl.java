package hello.core.member;

public class MemberServiceImpl implements MemberService{
//  MemberServiceImpl은 MemberRepository(추상화) 에도 의존하고, MemoryMemberRepository(구체화)에도 의존
//  => DIP 위반!
//  private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;
/*  MemberServiceImpl에 MemoryMemberRepository(구체화)에 대한 코드가 없음.
    오로지 MemberRepository(인터페이스)에 대한 것만 있음, 추상화에만 의존
    => DIP를 잘 지킴.
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
}
