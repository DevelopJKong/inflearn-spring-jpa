package jpabook.jpashopVer2.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashopVer2.domain.Delivery;
import jpabook.jpashopVer2.domain.Member;
import jpabook.jpashopVer2.domain.OrderItem;
import jpabook.jpashopVer2.domain.Order;
import jpabook.jpashopVer2.domain.item.Item;
import jpabook.jpashopVer2.repository.ItemRepository;
import jpabook.jpashopVer2.repository.MemberRepository;
import jpabook.jpashopVer2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * * 주문 발행하는 api
     * 
     * @param memberId 회원의 아이디
     * @param itemId   상품의 아이디
     * @param count    상품 개수
     * @return order.getId()
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // * 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // * 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // * 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // * 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /**
     * * 주문을 취소하는 api
     * 
     * @param orderId
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // * 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // * 주문 취소
        order.cancel();
    }

    // public List<Order> findOrders(OrderSearch orderSearch) {
    // return orderRepository.findAll(orderSearch);
    // }

}
