package com.ergun.basket.web;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BasketRequest {
    private final int customerId;
    private final int productId;
    private final int count;
    private double totalAmount;
    private int status;
    /*
    Her bir basketRequest'ten maksimum bir tane ürün ekleyebilir. Bundan dolayı da biz her gelen basket
    Request'te bir tane basketItemDtoList oluşturarak onun içine atıyoruz. Sonra da get(0) diyerek çekiyoruz.
    İşte bundan dolayı da productId alıyoruz request'ten!
    Neden get(0) diyoruz? Çünkü biliyoruz ki tek seferde maksimum bir tane ürün göndericek. Zaten o ürünüde
    dto içerisindeki listeye biz atıyoruz ve biliyoruz ki dto'daki ;
    private final List<BasketItemDto> basketItemList;
    alanındaki listede sadece bir tane eleman var.

     */

    // Service'e gitmeden önce çalışacak olan metod;
//    public BasketDto toDto(){
//        List<BasketItemDto> dtoList = new ArrayList<>();
//        BasketItemDto dto = BasketItemDto.builder().product(new Product(productId)).build();
//        dto.setCount(count);
//        dtoList.add(dto);
//        return BasketDto.builder()
//                .customerId(Customer.builder().shopAdminId(customerId).build())
//                .basketItemList(dtoList)
//                .build();
//    }
}
