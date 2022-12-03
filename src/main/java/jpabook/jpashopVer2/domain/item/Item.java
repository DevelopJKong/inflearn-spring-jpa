package jpabook.jpashopVer2.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import jpabook.jpashopVer2.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;

  private int price;

  private int stockQuantity;

  // ==비즈니스 로직==//
  /**
   * *stock 증가
   * 
   * @param quantity
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /**
   * *stock 감소
   * 
   * @param quantity
   */
  public void removeStock(int quantity) {
    int restStock = this.stockQuantity - quantity;
    if(restStock < 0) {
      throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = restStock;
  }
}
