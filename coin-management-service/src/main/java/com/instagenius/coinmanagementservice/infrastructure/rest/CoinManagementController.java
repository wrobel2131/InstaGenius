package com.instagenius.coinmanagementservice.infrastructure.rest;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.infrastructure.dto.AddCoinsDto;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinTransactionsResponseDto;
import com.instagenius.coinmanagementservice.infrastructure.dto.DeductCoinsDto;
import com.instagenius.coinmanagementservice.infrastructure.dto.UserBalanceResponseDto;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinTransactionMapper;
import com.instagenius.coinmanagementservice.infrastructure.mapper.UserBalanceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/coins")
@RequiredArgsConstructor
public class CoinManagementController {
    private final CoinManagementUseCase coinManagementUseCase;
    private static final CoinTransactionMapper coinTransactionMapper = CoinTransactionMapper.INSTANCE;
    private static final UserBalanceMapper userBalanceMapper = UserBalanceMapper.INSTANCE;


    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBalanceResponseDto> getBalance() {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        return ResponseEntity.ok(
                userBalanceMapper.toUserBalanceResponseDto(
                        coinManagementUseCase.getBalance(userId)
                )
        );
    }

    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addCoins(@Valid @RequestBody AddCoinsDto addCoinsDto) {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        coinManagementUseCase.addCoins(userId, addCoinsDto.coins(), addCoinsDto.type());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/deduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deductCoins(@Valid @RequestBody DeductCoinsDto deductCoinsDto) {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        coinManagementUseCase.deductCoins(userId, deductCoinsDto.coins());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinTransactionsResponseDto> getCoinTransactions() {
        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        return ResponseEntity.ok(
                new CoinTransactionsResponseDto(
                        coinManagementUseCase.getCoinTransactions(userId)
                                .stream()
                                .map(coinTransactionMapper::toCoinTransactionResponseDto)
                                .toList()
                )
        );
    }
}
