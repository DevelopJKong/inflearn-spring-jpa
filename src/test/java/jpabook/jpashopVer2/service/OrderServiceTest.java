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
import jpabook.jpashopVer2.exception.NotEnoughStockException;
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
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        // * when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // * then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다", 10000 * orderCount, getOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void notEnoughStock() throws Exception {
        // * given
        Member member = createMember();
        Book book = createBook("시골 JPA", 10000, 10); // 이름, 가격, 재고
        int orderCount = 11; // 재고보다 많은 수량
        // * when
        orderService.order(member.getId(), book.getId(), orderCount);
        // * then
        Assert.fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void cancel() throws Exception {
        // * given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // * when
        orderService.cancelOrder(orderId);

        // * then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 상품을 그만큼 재고가 증가해야 한다", 10, item.getStockQuantity());

    }

    @Test
    public void overStock() throws Exception {
        // * given

        // * when

        // * then
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

}
