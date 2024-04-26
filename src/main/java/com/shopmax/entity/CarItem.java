package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@ToString
public class CarItem extends BaseEntity {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int count; // 장바구니에 담긴 상품수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id") //FK키
    private Cart cart; //CartItem은 Cart를 참조한다

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id") //FK키
    private Item item; //CartIten은 Item을 참조한다

}
