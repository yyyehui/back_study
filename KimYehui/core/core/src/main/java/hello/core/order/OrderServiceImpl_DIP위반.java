package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl_DIP위반 implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //위와 같이 변경하면서, RateDiscuntPolicy에 대한 부분을 모두 수정해야 함
    //=> DIP 위반 : 인터페이스(역할) 뿐 아니라 구체 클래스(구현)에도 의존하고 있음
    //=> OCP 위반 : 변경에 닫혀 있지 못하다 (기능 변경해서 확장하면 클라이언트 코드에 영향)

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
