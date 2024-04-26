package com.shopmax.service;

import com.shopmax.dto.OrderDto;
import com.shopmax.entity.Item;
import com.shopmax.entity.Member;
import com.shopmax.entity.Order;
import com.shopmax.entity.OrderItem;
import com.shopmax.repository.ItemRepository;
import com.shopmax.repository.MemberRepository;
import com.shopmax.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        //1. 주문한 상품의 item객체를 가져온다.
        Item item = itemRepository
                .findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        //2. 현재 로그인한 회원의 이메일을 이용해 member 엔티티를 가져온다.
        Member member = memberRepository.findByEmail(email);

        //양방향 관계일때 save
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        //양방향이든 단방향이든 참조하는 객체를 무조건 넣은 후 save를 진행한다.
        //양방향일때는 부모 엔티티 객체가 참조하는 자식 객체를 넣은 후 save
        //단방향일때는 자식 엔티티 객체가 참조하는 부모 객체를 넣은 후 save
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order); //insert

        return order.getId();
    }
}
