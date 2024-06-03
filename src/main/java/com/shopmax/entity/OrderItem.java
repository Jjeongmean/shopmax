package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders_item")
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity {
    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int orderPrice; //주문가격

    private int count; //주문 수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //OrderItem이 Item을 참조한다

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order; //OrderItem이 Order를 참조한다

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count); //item객체 안의 재고변경

        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count; //총 가격
    }

    //재고를 원래대로
    public void cancel() {
        //이전에 주문을 한 만큼(count) 다시 item의 stock에 더해준다.
        this.getItem().addStock(count);
    }
}
