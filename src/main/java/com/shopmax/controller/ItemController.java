package com.shopmax.controller;

import com.shopmax.dto.ItemFormDto;
import com.shopmax.dto.ItemSearchDto;
import com.shopmax.dto.MainItemDto;
import com.shopmax.entity.Item;
import com.shopmax.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        //valid 체크를 위해 비어있는 DTO 객체를 꼭 넣어준다
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    //상품 등록 처리(insert)
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          //RequestParam: input 태그의 name 작성, 이미지를 5개 받아오기에 List로 받는다
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if (bindingResult.hasErrors()) return "item/itemForm"; //유효성 체크에서 걸리면

        //상품 등록 전에 첫번째 이미지가 있는지 없는지 검사(첫번째 이미지는 필수 입력값)
        if (itemImgFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage",
                    "첫번째 상품 이미지는 필수 입력값입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품등록 중 에러가 발생했습니다.");
            return "item/itemForm";

        }
        return "redirect:/"; //상품등록이 문제없이 완료되면 index화면으로 이동
    }

    //상품 수정페이지 보기
    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품정보를 가져오는 도중 에러가 발생했습니다.");

            //에러발생시 비어있는 객체를 넘겨준다.
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemModifyForm"; //등록화면으로 다시 이동
        }

        return "item/itemModifyForm";
    }

    //상품 수정(update)
    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, Model model, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             @PathVariable("itemId") Long itemId) {

        if (bindingResult.hasErrors()) return "item/itemForm"; //유효성 체크에서 걸리면

        ItemFormDto getItemFormDto = itemService.getItemDtl(itemId);

        //상품등록 전에 첫번째 이미지가 있는지 없는지 검사(첫번째 이미지는 필수 입력값)
        //itemFormDto.getId() == null -> 이미지 외에 다른 내용만 수정했을때 if문에 걸리는 경우를 방지
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage",
                    "첫번째 상품 이미지는 필수 입력입니다.");
            model.addAttribute("itemFormDto", getItemFormDto);
            return "item/itemModifyForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "상품 수정중 에러가 발생했습니다.");
            model.addAttribute("itemFormDto", getItemFormDto);
            return "item/itemModifyForm";
        }

        return "redirect:/";

    }

    //상품 관리 페이지
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page")Optional<Integer> page, Model model) {

        //PageRequest.of(페이지 번호, 한페이지당 조회할 데이터 갯수)
        //URL path에 페이지가 있으면 해당 페이지 번호를 조회하고
        // 페이지 번호가 없다면 0 페이지(첫번째 페이지)를 조회
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3);

        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);

        //상품관리 페이지 하단에 보여줄 최대 페이지 번호
        model.addAttribute("maxPage", 5);


        return "item/itemMng";
    }

    //상품 전체 리스트
    @GetMapping(value = "/item/shop")
    public String itemShopList(Model model, ItemSearchDto itemSearchDto,
                               @RequestParam(value = "page") Optional<Integer> page) {
        //한 페이지 당 6개의 게시물 보여줌.
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemShopList";
    }

    //상품 상세 페이지
    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }
}