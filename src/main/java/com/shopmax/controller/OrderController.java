package com.shopmax.controller;

import com.shopmax.dto.OrderDto;
import com.shopmax.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //url -> GetMapping
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(
            // OrderDto는 ajax에서 전달해준 데이터를 받아온다,
            // http통신에서 body에 담겨져서 오므로 반드시 @RequestBody 어노테이션 추가
            // Principal: 로그인한 사용자의 정보를 가져온다.
            @RequestBody @Valid OrderDto orderDto,
            BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            //유효성 체크 후 에러 결과를 가져온다.
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()); //에러메세지를 가지고온다.
            }

            //new ResponseEntity<첫번째 매개변수의 타입>(response 데이터, response status 코드);
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);

        }


        String email = principal.getName(); //id를 가져온다(email)
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); //주문하기
        } catch (Exception e) {
            return new ResponseEntity<String >(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK); //성공시



    }
}
