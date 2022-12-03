package jpabook.jpashopVer2.service;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashopVer2.domain.Address;
import jpabook.jpashopVer2.domain.Member;
import jpabook.jpashopVer2.domain.Order;
import jpabook.jpashopVer2.domain.OrderStatus;
import jpabook.jpashopVer2.domain.item.Book;
import jpabook.jpashopVer2.domain.item.Item;
import jpabook.jpashopVer2.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void order() throws Exception {
        // * given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        // * when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // * then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
    }

    @Test
    public void cancel() throws Exception {
        // * given

        // * when

        // * then
    }

    @Test
    public void overStock() throws Exception {
        // * given

        // * when

        // * then
    }

}
