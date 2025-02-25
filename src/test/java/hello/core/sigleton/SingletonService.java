package hello.core.sigleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
//  1. static 영역에 객체 instance를 미리 하나 생성해서 올려둔다.

    public static SingletonService getInstance() {
        return instance;
    }
/*
2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만
조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
 */

    private SingletonService() {
    }
/*
3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로
막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다
 */

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
/*
sigleton패턴의 문제점
1. 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
2. 의존관계상 클라이언트가 구체 클래스에 의존한다. => DIP 위반
3. 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
4. 테스트하기 어렵다.
5. 내부 속성을 변경하거나 초기화 하기 어렵다.
6. private 생성자로 자식 클래스를 만들기 어렵다.
7. 결론적으로 유연성이 떨어진다.
8. 안티패턴으로 불리기도 한다.

 */