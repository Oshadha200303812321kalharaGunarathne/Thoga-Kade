package model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {

    private String itemCode;
    private String description;
    private String packSize;
    private String unitPrice;
    private String qtyOnHand;
}
