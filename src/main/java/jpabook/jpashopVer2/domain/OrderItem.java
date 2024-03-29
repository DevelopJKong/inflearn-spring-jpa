package jpabook.jpashopVer2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jpabook.jpashopVer2.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice; // 주문 가격

  private int count; // 주문 수량

  // ! 생성 메서드
  /**
   * * 주문 상품을 생성하는 api
   * 
   * @param item       상품
   * @param orderPrice 주문 가격
   * @param count      상품의 개수
   * @return orderItem
   */
  public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);

    item.removeStock(count);
    return orderItem;
  }

  // ! 비즈니스 로직
  public void cancel() {
    getItem().addStock(count);
  }

  // ! 조회 로직
  public int getTotalPrice() {
    return getOrderPrice() * getCount();
  }
}
