package hello.core.member;

public interface MemberService {

    void join(Member member);
    // 회원 가입
    Member findMember(Long memberid);
    // 회원 조회
}
