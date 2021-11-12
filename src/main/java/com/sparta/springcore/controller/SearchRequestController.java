package com.sparta.springcore.controller;

import com.sparta.springcore.dto.ItemDto;
import com.sparta.springcore.dto.NaverItemDto;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.feign.NaverService;
import com.sparta.springcore.util.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // JSON으로 응답함을 선언합니다.
@RequiredArgsConstructor
public class SearchRequestController {

    @Value("${naver.clientId}")
    private String clientId;

    @Value("${naver.clientSecret}")
    private String clientSecret;

    private final NaverService naverService;

    @GetMapping("/api/search")
    public NaverItemDto getItems(@RequestParam String query) {
        return naverService.search(clientId, clientSecret, query);
    }
}
