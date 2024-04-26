package com.shopmax.repository;

import com.shopmax.dto.ItemSearchDto;
import com.shopmax.dto.MainItemDto;
import com.shopmax.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
* query dsl 사용시 3가지 과정
* 1. 사용자정의 인터페이스 구현
* 2. 사용자정의 인터페이스 작성
* 3. Spring Date JPA 레파지토리에서 사용자 정의 인터페이스 상속
 */

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
