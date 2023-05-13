package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // 아이템을 저장하는 repository와 그 기능을 열거
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public Item save(Item item) { // 저장
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) { // 아이디로 아이템 찾기
        return store.get(id);
    }

    public List<Item> findAll() { // 모든 아이템 찾기
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) { // 아이템 수정
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() { // repo 비우기
        store.clear();
    }

}
