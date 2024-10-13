package com.ergun.basket.impl;

import com.ergun.auth.customer.api.CustomerDto;
import com.ergun.basket.api.AddBasketDto;
import com.ergun.basket.api.BasketDto;
import com.ergun.basket.api.BasketService;
import com.ergun.basket.api.ProductDto;
import com.ergun.basket.feign.ProductFeign;
import com.ergun.basket.impl.basketitem.BasketItem;
import com.ergun.basket.impl.basketitem.BasketItemServiceImpl;

import com.ergun.stock.product.web.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository repository;
    private final BasketItemServiceImpl basketItemService;
    private final ProductFeign productFeign;


    public final int BASKET_STATUS_NONE = 0;
    public final int BASKET_STATUS_SALED = 1;

    /*
    1-) basket yok direkt ürünü ekle
    2-) Basket var
           Eklenen ürün sepette varmı
                1-) varsa quantity artır total amount artır
                2-) yoksa yeni ürünü ekle
     */
    public Optional<CustomerDto> getCustomerById(String customerId) {
        RestTemplate restTemplate = new RestTemplate();
        CustomerDto customer = restTemplate.getForObject("http://localhost:8080/customers" + customerId, CustomerDto.class);
        return Optional.of(customer);
    }


    @Override
    public BasketDto addProductToBasket(AddBasketDto basketDto) {
        CustomerDto customerDto = getCustomerById(String.valueOf(basketDto.getCustomerId())).orElseThrow(() -> new RuntimeException("Category not found"));
        Basket basket = repository.findBasketByCustomerIdAndStatusEquals(customerDto.getCustomerId(), BASKET_STATUS_NONE);
        if (basket != null) {
            // Basket Var Senaryosu
            return sepetVarUrunEkle(basket, basketDto);
        } else {
            // Basket yok senaryosu
            return sepetYokYeniSepetOlustur(basketDto, customerDto);
        }
    }


    @Override
    public BasketDto getBasketByCustomerId(String customerId) {
        Basket basket = repository.findBasketByCustomerIdAndStatusEquals(Integer.parseInt(customerId), BASKET_STATUS_NONE);
        return toDto(basket);
    }

    @Override
    public void removeProductFromBasket(String basketItemId) {
        basketItemService.delete(Integer.parseInt(basketItemId));
    }

    private BasketDto sepetYokYeniSepetOlustur(AddBasketDto basketDto, CustomerDto customer) {
        List<BasketItem> basketItemList = new ArrayList<>();
        Basket basket = new Basket();
        basket.setCustomerId(customer.getCustomerId());
        basket.setStatus(BASKET_STATUS_NONE);
        basket=repository.save(basket);
        ProductDto product = mapToProductDto(productFeign.get(String.valueOf(basketDto.getProductId())));
        if (product == null) {
            throw new RuntimeException("product not found");
        }
        BasketItem newBasketItem=createBasketItem(product,basket,basketDto);
        basketItemList.add(newBasketItem);
        basket.setTotalAmount(calculateBasketAmount(basket.getBasketId()));

        basket.setBasketItemList(basketItemList);
        repository.save(basket);
        return toDto(basket);
    }

    private BasketDto sepetVarUrunEkle(Basket basket, AddBasketDto basketDto) {
        List<BasketItem> basketItemList = basket.getBasketItemList();
        // Aşağıdaki kod satırı yerine gelen listeyi for ile dönebilirdik fakat bunu yapmak yerine repository'e metod yazıp oradan var mı yok mu kontrolünü yapmak bestPractice.
        BasketItem basketItem = basketItemService.findBasketItemByBasketIdAndProductId(basket.getBasketId(), basketDto.getProductId());

        if (basketItem == null) {
            System.out.println("Eklenen ürün sepette yok");
              // Product product = basketItem.getProduct(); Hoca bunu yazdı yüksek ihtimalle yanlış var!
            ProductDto product = mapToProductDto(productFeign.get(String.valueOf(basketDto.getProductId())));
            if (product == null) {
                throw new RuntimeException("product not found");
            }
           BasketItem newBasketItem=createBasketItem(product,basket,basketDto);
//            newBasketItem = basketItemService.save(newBasketItem);
            basketItemList.add(newBasketItem);
        }
        else {
//            System.out.println("Eklenen ürün sepette var");
//            System.out.println("liste var mı " + basketDto.getBasketItemList());
//            System.out.println("BasketİtemList boş mu" + basketDto.getBasketItemList().get(0).getProduct().getName());
//            System.out.println("BasketItem : " + basketItem);
            // Eklenen ürün sepette var
            ProductDto product = mapToProductDto(productFeign.get(String.valueOf(basketDto.getProductId())));
            if (product == null) {
                throw new RuntimeException("product not found");
            }
            basketItem.setProductId(product.getProductId());
            basketItem.setCount(basketItem.getCount() + basketDto.getCount());
            basketItem.setBasketItemAmount(basketItem.getCount() * product.getPrice());
            basketItem.setBasket(basket);

        }

        basket.setTotalAmount(calculateBasketAmount(basket.getBasketId()));
        basket.setBasketItemList(basketItemList);
        repository.save(basket);

        return toDto(basket);

    }

    private ProductDto mapToProductDto(ProductResponse productResponse) {
        return ProductDto.builder()
                .productId(productResponse.getProductId())
                .name(productResponse.getName())
                .price(productResponse.getPrice())
                .stock(productResponse.getStock())
                .categoryId(productResponse.getCategoryId()).build();
    }

    private BasketItem createBasketItem(ProductDto product, Basket basket,AddBasketDto basketDto) {
        BasketItem newBasketItem=new BasketItem();
        newBasketItem.setProductId(product.getProductId());
        newBasketItem.setCount(basketDto.getCount());
        newBasketItem.setBasketItemAmount(newBasketItem.getCount() * product.getPrice());
        newBasketItem.setBasket(basket);
        return newBasketItem;
    }


//    private Optional<ProductDto> getProductById(int productId) {
//        RestTemplate restTemplate = new RestTemplate();
//        ProductDto product = restTemplate.getForObject("http://localhost:8081/stock" + productId, ProductDto.class);
//        return Optional.of(product);
//    }

    // Bu metod sepette daha önceden ürün varsa çalışır
    private double calculateBasketAmount(int basketId) {
        Basket basket = repository.findBasketByBasketId(basketId);
        double totalAmount = 0.0;
        for (BasketItem basketItem : basket.getBasketItemList()) {
            totalAmount += basketItem.getBasketItemAmount();
        }
        return totalAmount;
    }

    /*
    Yukarıdaki metodda neden Basket basket almıyorda basketId alıyor?
    Çünkü BasketItem'ı DB'te güncelledim. Güncelledim Basket güncellendi ama bende güncel basket yok! Yani sepetVarUrunEkle() metoduna parametre olarak
    gönderilen basket güncel değil! Bunun güncelini almam gerekiyor! Sen BasketItem'i güncellediğin an zaten Repository Basket'ını da güncelliyor. Yani
    senin BasketRepository'den güncel basket'ı çekmen gerekiyor.
     */


    // Response'tan önce çalışacak olan metod
    public BasketDto toDto(Basket basket) {
        return BasketDto.builder()
                .basketId(basket.getBasketId())
                .totalAmount(basket.getTotalAmount())
                .customerId(basket.getCustomerId())
                .status(basket.getStatus())
                .basketItemList(basket.getBasketItemList().stream().map(basketItem -> basketItemService.toDto(basketItem)).collect(Collectors.toList()))
                .build();
    }

    // Repository'e gidicek metod
    public Basket toEntity(BasketDto basketDto) {
        Basket basket = new Basket();
        basket.setTotalAmount(basketDto.getTotalAmount());
        basket.setStatus(basketDto.getStatus());
        basket.setCustomerId(getCustomerById(String.valueOf(basketDto.getCustomerId())).orElseThrow(() -> new RuntimeException("Customer not found")).getCustomerId());
        return basket;
    }


}
