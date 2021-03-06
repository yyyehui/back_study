<전반적>
- 패키지명은 소문자로 시작, class/interface는 대문자로 시작 (카멜형식으로)
- primitive type(Long아닌 long 타입)을 써도되지만 그러면 null을 넣을 수 없어서 사용안함

<JAVA>
- final은 최종이므로 값 할당되어야만 하며 변경 불가

<초기 설정>
- start.io에서 generate해서 가져온것 build.gradle 을 인텔리제이로 열기
 @ 이때, 초기 그래들 빌드 안되는 에러 해결 
  => https://i5i5.tistory.com/186
  => https://tsy0668.tistory.com/4
- gradle 검색 후, build and run using, run tests using 둘 다 intellj로 설정

<jUnit을 사용해서 스프링 기능 본격 활용해 단위테스트하기!>
- ~App.java 먼저 굳이 만들 필요 없이 바로 테스트해도 되지만 그래도 좀더 익숙해지라고 했다고 하심.
- test 내의 메서드의 골격 구성은 보통 다음과 같이 잡으신다고 함
        //given
        //when
        //then
- assertion(org.~j.core.api.~)으로 확인
- testcode 작성은 정말 중요하다!!
- 초기에 자동으로 생성되어 있는 CoreApplicationTests는 SpringBootTest라서 화면 띄우고 스프링부트 뭐 어쩌구 하느라 테스트할때마다 실행 오래걸림. 그래서 단위테스트(테스트 패키지 내에 각 패키지랑 테스트클래스 따로 생성해서 테스트)해주는 것이 중요하다! 
*  여기서의 단위테스트 : 스프링 등의 도움 없이 순수하게 자바 코드로 테스트 하는 것
- jupiter api의 DisplayName에는 한글 사용 가능 (ex. @DisplayName("VIP는 10% 할인이 적용되어야 한다") ) => 콘솔에 이대로 뜸
- 테스트 코드에서 @BeforeEach는 각 테스트를 실행하기 전에 호출된다

<여기까지(회원정보설계)의 문제점>
- 다른 저장소로 변경시 OCP, DIP원칙 어겨짐
=> 의존관계가 인터페이스 뿐 아니라, 모든 서비스에 의존하는 문제점 (즉, 추상화, 구체화 모두에 의존한다)

<주문, 할인 설계>
- 역할과 구현 분리해서, 자유롭게 구현 객체 조립 가능. => 그래서 적용할지말지가 미확정된 할인 정책도 유연하게 변경 가능
- toString사용하면(자동완성으로 만들기 가능) 전체 클래스 객체를 호출하면 toString 메서드가 호출되어, 전체가 보기쉽게 println 됨.
- OrderServiceImpl은 역할 구현 잘 분리되었지만, 아직도 OCP, DIP 원칙 어겨지고 있다

<여기까지(할인설계)의 문제점>
- 할인 정책 변경으로 인해, 호출한 클래스 메서드를 변경하게 되는데 이는 OCP, DIP 위반함 (앞서 언급한 회원정보설계도 마찬가지로 여전히 위반중임)
=> DIP 위반 : 인터페이스(역할) 뿐 아니라 구체 클래스(구현)에도 의존하고 있음
=> OCP 위반 : 변경에 닫혀 있지 못하다 (기능 변경해서 확장하면 클라이언트 코드에 영향)
- 해결을 위해선, 누군가 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성 및 주입해줘야 한다.

<관심사의 분리 = AppConfig의 등장>
- 배우가 직접 각 역할을 캐스팅할 필요 없이, 공연기획자를 만들어서, 배우와의 역할을 분리 -> AppConfig의 역할
- 객체를 생성하고 연결하는 역할과, 실행하는 역할의 명확한 분리

<AppConfig을 통한 DIP성립 및 DI>
: 애플리케이션의 전체 동작 방식을 구성하기 위해, 구현객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스
- 실제 동작에 필요한, "구현 객체를 생성"
 ex) MemberServiceImp, MemoeryMemberRepository, OrderServiceImp, FixDiscountPolicy
- 생성한 객체 인스턴스의 참조(레퍼런스)를 "생성자를 통해 주입(연결)"해줌
 ex) MemberServiceImpl -> MemoryMemberRepository
  -> 설계 변경으로 MemberServiceImpl은 MemoeryMemberRepository에 의존하지 않는다
  -> 단지, MemberRepository 인터페이스에 의존한다
  -> MemberServiceImpl 입장에서는, 생성자를 통해 어떤 구현 객체 주입될지 알 수 없다
  -> MemberServiceImpl의 생성자 통해서 어떤 객체 주입할지는 오직 외부(=AppConfig)에서 결정된다
  -> MemberServiceImpl은 이제부터, "의존관계에 대한 고민은 외부에 맡기고, 실행에만 집중"하면 된다
  => MemberServiceImpl은 MemberRepository인 추상에만 의존하면 되고, 구체 클래스를 몰라도 된다!
  => DIP 성립! 
 ex) OrderServiceImpl -> MemoryMemberRepository 또는 FixDiscountPolicy
  -> 설계 변경으로 OrderServiceImpl은 FixDiscountPolicy에 의존하지 않는다
  -> 단지, FixDiscountPolicy 인터페이스에 의존한다
  -> OrderServiceImpl 입장에서는, 생성자를 통해 어떤 구현 객체 주입될지 알 수 없다
  -> OrderServiceImpl의 생성자 통해서 어떤 객체 주입할지는 오직 외부(=AppConfig)에서 결정된다
  -> OrderServiceImpl은 이제부터, "의존관계에 대한 고민은 외부에 맡기고, 실행에만 집중"하면 된다
  => DIP 성립!
=> 이렇게, 의존관계를 외부에서 주입해주는 것을 "DI(Dependency Injection), 의존관계 주입"이라고 한다
(DI를 통해 DIP, OCP 성립해줌)

<AppConfig 리팩토링>
(리팩토링전)
- 현재 AppConfig는 중복이 있고, 역할에 따른 구현이 잘 안보인다.
(리팩토링후(코드참고))
- 이제, AppConfig를 보면 역할과 구현 클래스 한눈에 들어오며, 애플리케이션 전체 구성을 빠르게 파악가능하다.
- 중복이 사라졌다.
- MemoryMemberRepository를 다른 구현체로 변경하거나, 할인정책 구현체 변경할때, 한 부분만 변경하면 된다.
- 추후, appconfig를 xml 형식으로 바꾸면, java 코드 손대지도 않고 변경이 가능해짐
=> SRP, DIP, OCP 적용

------------------------------------------------------------------------------

<단축키>
- 단축키 보는 법 : preferences에서 keymap
- alt + insert : getter, setter / implements method 등 자동 완성
- ctrl + shift + enter : 코드 자동 완성 시 세미콜론까지 찍히게 해주는 단축키
- psvm 치고 enter : public static void main 자동 완성
- ctrl + alt + v : new 객체a 이하만 작성하고 객체a 뒤에서 해당 단축키 써주면 앞부분은 자동 완성 
- soutv 치고 enter : 위 객체 출력println하는 단축키
- F2 : 오류난 곳으로 바로 이동
- /** 입력후 enter : 주석 한 세트로 자동 완성됨
- ctrl + shift + t : 메서드명 위에서 클릭시, test 만들기 창 뜸 => test 패키지 내에 같은 경로로 test 클래스 생성해줌
- alt + enter : 호출한 메서드 앞에서 클릭시, ondemand static method로 고정(해당 클래스에서 정적 메소드로 고정) => ex) Assertions.assertThat을 assertThat으로 생략 가능 (static import를 찾아보라)
- ctrl + e : 최근 메서드 작업 기록 나옴
- ctrl + shift + f : 전체 검색
- ctrl + shift + r : 전체 검색 및 replace

<내가 필요에 의해 찾은 단축키>
- ctrl + y : 줄삭제
- ctrl + d : 줄복제
- shift + Enter : 커서 아랫줄에 빈줄 추가
- ctrl + r : 바꾸기
- ctrl + shift + z : redo
- ctrl + delete/backSpace : 커서 뒷단어/앞단어 삭제
http://ngknightlamune.blogspot.com/2017/06/intellij.html

<기능>
- 패키지 펼치는 방식 변경 : 프로젝트 쪽 톱니바퀴 tree appearance

<실무에서는>
- 그냥 hashmap 말고 concurrentHashMap(?) 사용!

<찾아볼 것>
- concurrentHashMap

