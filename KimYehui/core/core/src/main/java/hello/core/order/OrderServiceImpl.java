package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;
    // 위에 까지만 하고 끝내면 연결된 메소드가 없어서 nullpointException발생
    // 누군가 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성 및 주입해줘야 한다

    // 생성자를 통해 위 memberRepositoy를 뭘로 가져올지 선택함
    // => 생성자 주입 : 생성자를 통해 객체를 주입하여 DIP 성립 (여기선 연결된 애가 뭔지 알수가 없음)
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 싱글톤 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
