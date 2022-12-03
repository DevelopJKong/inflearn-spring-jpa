package jpabook.jpashopVer2.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashopVer2.domain.item.Item;
import jpabook.jpashopVer2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * * 상품 저장 api
     * 
     * @param item
     */
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * * 상품들 전체를 가지고 오는 api
     * 
     * @return List<Item>
     */
    public List<Item> findItems() {
        List<Item> result = itemRepository.findAll();
        return result;
    }

    /**
     * * 상품 하나를 가지고 오는 api
     * 
     * @param itemId
     * @return Item
     */
    public Item findOne(Long itemId) {
        Item result = itemRepository.findOne(itemId);
        return result;
    }
}
