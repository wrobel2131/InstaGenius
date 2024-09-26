package com.instagenius.coinmanagementservice.infrastructure.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coin-management")
@RequiredArgsConstructor
public class CoinManagementController {
    private final CoinManagementUseCase coinManagementUseCase;


    @GetMapping(value = "/")
}
