package hello.core.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 생성자를 통해 위 memberRepositoy를 뭘로 가져올지 선택함
    // => 생성자 주입 : 생성자를 통해 객체를 주입하여 DIP 성립
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

    // 싱글톤 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

