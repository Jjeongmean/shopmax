package com.shopmax.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc //mockMvc 테스트를 위해 어노테이션 선언
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemControllerTest {
    @Autowired
    MockMvc mockMvc; //모형객체 -> request가 오는 것처럼 만들어준다

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "USER")
    public void itemFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //get 요청을 보낸다
                .andDo(MockMvcResultHandlers.print()) //요청과 응답 메세지를 확인할 수 있도록 콘솔창에 출력
                .andExpect(MockMvcResultMatchers.status().isOk()); //응답 http 상태코드가 정상이면 테스트 통과
    }

    @Test
    @DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
    @WithMockUser(username = "admin", roles = "USER")
    public void itemFormNotAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //get 요청을 보낸다
                .andDo(MockMvcResultHandlers.print()) //요청과 응답 메세지를 확인할 수 있도록 콘솔창에 출력
                .andExpect(MockMvcResultMatchers.status().isForbidden()); //응답 http 상태코드가 Forbidden이면 테스트 통과
    }

}
