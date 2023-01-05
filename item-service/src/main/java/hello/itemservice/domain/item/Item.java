package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer prive, Integer quantity) {
        this.itemName = itemName;
        this.price = prive;
        this.quantity = quantity;
    }
}
