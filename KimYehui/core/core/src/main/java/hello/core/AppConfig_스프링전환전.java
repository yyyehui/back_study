package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig_스프링전환전 {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    // 아래 구조 통해, new로 생성되는 객체의 중복 사라지고, 역할에 따른 구현 확실해짐
    // => repository가 바뀌면 아랫부분만 바꿔주면 됨!
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(),  discountPolicy());
    }

    // 아래 구조 통해, new로 생성되는 객체의 중복 사라지고, 역할에 따른 구현 확실해짐
    // => 할인정책 바뀌면 아랫부분만 바꿔주면 됨!
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
