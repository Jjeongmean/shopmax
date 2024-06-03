package com.shopmax.dto;

import com.shopmax.constant.OrderStatus;
import com.shopmax.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {
    //엔티티 -> Dto로 변환
    //OrderDate에 format을 써야하므로 modelMapper를 사용 X
    public OrderHistDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }
    private Long orderId; //주문아이디 (무조건 필수(구매취소시)

    private String orderDate; //주문 날짜

    private OrderStatus orderStatus; //주문상태

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>(); //주문 상품 리스트

    public void addOrderItemDto (OrderItemDto orderItemDto) {
        this.orderItemDtoList.add(orderItemDto);
    }

}
