package com.sparta.springcore.service.feign;

import com.sparta.springcore.dto.NaverItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "naver", url = "${naver.url}")
public interface NaverService {
    @GetMapping(path = "/v1/search/shop.json", consumes = "application/json")
    NaverItemDto search(@RequestHeader("X-Naver-Client-Id") String clientId,
                        @RequestHeader("X-Naver-Client-Secret") String clientSecret,
                        @RequestParam("query") String query);
}
