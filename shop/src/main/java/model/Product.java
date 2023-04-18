package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Product {
    private int id;
    private String category;
    private String gender;
    private String name;
    private long price;
    private long quantity;
}
