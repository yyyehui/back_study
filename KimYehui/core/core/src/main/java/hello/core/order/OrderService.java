package hello.core.order;

public interface OrderService {
    // 회원번호, 아이템명, 아이템가격 넣으면 Order 객체를 생성한다
    Order createOrder(Long memberId, String itemName, int itemPrice);

}
