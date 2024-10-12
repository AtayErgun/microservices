package com.ergun.basket.web;


import com.ergun.basket.api.AddBasketDto;
import com.ergun.basket.api.BasketDto;
import com.ergun.basket.api.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PostMapping
    public BasketResponse addProductToBasket(@RequestBody BasketRequest basketRequest){
        return toResponse(basketService.addProductToBasket(AddBasketDto.builder().productId(basketRequest.getProductId())
                .customerId(basketRequest.getCustomerId())
                .count(basketRequest.getProductId()).build()));
    }

    @GetMapping("/{customerId}")
    public BasketResponse getBasketByCustomerId(@PathVariable String customerId){
        return toResponse(basketService.getBasketByCustomerId(customerId));
    }

    @DeleteMapping("/{basketItemId}")
    public String delete(@PathVariable String basketItemId){
        basketService.removeProductFromBasket(basketItemId);
        return "Silme işlemi başarılı";
    }

    public BasketResponse toResponse(BasketDto basketDto) {
        return BasketResponse.builder()
                .basketId(basketDto.getBasketId())
                .totalAmount(basketDto.getTotalAmount())
                .customerId(basketDto.getCustomerId())
                .basketItemList(basketDto.getBasketItemList())
                .build();
    }
}
