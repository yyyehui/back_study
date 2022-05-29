package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor    // 롬복 기능 사용
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //  [chapter 7] 1. 생성자 주입
        @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {   // 주문 생성 요청이 오면
        Member member = memberRepository.findById(memberId);    // 회원 정보를 먼저 생성하고
        int discountPrice = discountPolicy.discount(member, itemPrice); // 회원별 할인 정책 적용 후
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // Chapter 6. @Configuration과 싱글톤 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }






//    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy;
//
//
//    //  [chapter 7] 1. 생성자 주입
//    //    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

//    // [chapter 7] 2. 수정자 주입 (선택 x)
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    //  [chapter 7] 2. 수정자 주입
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }
//
//    // [chapter 7] 3. 필드 주입
//    @Autowired private  MemberRepository memberRepository;
//    @Autowired private  DiscountPolicy discountPolicy;

////    // [chapter 7] 4. 일반 메서드 주입
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

}
