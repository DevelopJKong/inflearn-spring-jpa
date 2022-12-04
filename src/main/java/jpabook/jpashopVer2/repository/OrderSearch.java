package jpabook.jpashopVer2.repository;

import jpabook.jpashopVer2.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
    
}
