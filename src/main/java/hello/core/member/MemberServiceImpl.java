package hello.core.member;

public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;   // memberRepository 의 생성자를 통해 구현체를 정의함 [생성자 주입]
                                                    // 기획자를 통해 배역을 정함, 오로지 추상화에만 의존함, DIP를 준수함
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
