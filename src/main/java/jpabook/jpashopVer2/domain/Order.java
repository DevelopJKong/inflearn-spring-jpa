package jpabook.jpashopVer2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member; // 주문 회원

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "delivery_id")
  private Delivery delivery; // 배송정보

  private LocalDateTime orderDate; // 주문시간

  @Enumerated(EnumType.STRING)
  private OrderStatus status; // 주문상태 [ORDER, CANCEL]

  // ! 연관관계 메서드

  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  // ! 생성 메서드
  // TODO ... 에 대해서 정확하게 알아두어야 할거 같다

  /**
   * * 주문을 생성하는 api
   * 
   * @param member     회원
   * @param delivery   배달
   * @param orderItems 주문상품 모음
   * @return order
   */
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);
    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  // ! 비즈니스 로직
  public void cancel() {
    if (delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다");
    }
    this.setStatus(OrderStatus.CANCEL);
    for (OrderItem orderItem : orderItems) {
      orderItem.cancel();
    }
  }

  // ! 조회 로직
  /**
   * * 전체 주문 가격 조회
   * 
   * @return totalPrice
   */
  public int getTotalPrice() {
    int totalPrice = 0;
    for (OrderItem orderItem : orderItems) {
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;
  }
}
