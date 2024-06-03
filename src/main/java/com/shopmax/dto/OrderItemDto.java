package com.shopmax.dto;

import com.shopmax.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    //엔티티 -> Dto로 바꿔준다.
    //OrderItem엔티티와 OrderItemDto는 속성이 일치하지 않으므로 modelMapper를 사용X
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getOrderPrice();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String itemNm; //상품명
    private int count; //주문 수량
    private int orderPrice; //주문금액
    private String imgUrl; //상품 이미지 경로
}
