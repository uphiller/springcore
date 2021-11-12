package com.sparta.springcore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverItemDto {
    List<Item> items;

    @Getter
    @Setter
    public static class Item {
        String title;
        String link;
        String image;
        String lprice;
        String hprice;
        String mallName;
        String productId;
        String productType;
        String brand;
        String maker;
        String category1;
        String category2;
        String category3;
        String category4;
    }
}
