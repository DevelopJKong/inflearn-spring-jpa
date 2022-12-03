package jpabook.jpashopVer2.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashopVer2.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * * 상품 저장 api
     * 
     * @param item
     */
    public void save(Item item) {
        // 처음에는 아이디가 없는데 아이디가 없다는것은 완전히 새로 생성한것이다 라고 이해하면 됨
        if (item.getId() == null) {
            em.persist(item); // ? 여기서 persist가 멈춤 느낌인가?
        } else {
            em.merge(item); // ? update 라고 생각하면 좋음
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class) //
                .getResultList();
    }

}
