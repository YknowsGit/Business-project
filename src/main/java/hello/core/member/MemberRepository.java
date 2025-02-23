package hello.core.member;

public interface MemberRepository {

    void save(Member member);
    // 회원 저장 기능
    Member findById(Long memberid);
    // 회원의 id로 아이디를 찾는 기능
}
