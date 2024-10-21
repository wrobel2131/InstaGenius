package com.instagenius.coinmanagementservice.infrastructure.rest;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.infrastructure.dto.*;
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

    private final UUID testUUID = UUID.fromString("b1cfa5c1-5525-48ea-8899-07726b409197");

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBalanceResponseDto> getBalance() {
//        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        UUID userId = testUUID;
        return ResponseEntity.ok(
                userBalanceMapper.toUserBalanceResponseDto(
                        coinManagementUseCase.getBalance(userId)
                )
        );
    }

    @PostMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBalanceResponseDto> createBalance(@Valid @RequestBody CreateUserBalanceDto createUserBalanceDto) {
//        UUID userId = UUID.randomUUID(); //TODO getUUID from Token
        UUID userId = testUUID;
        return ResponseEntity.ok(
                userBalanceMapper.toUserBalanceResponseDto(
                        coinManagementUseCase.createBalance(userId, createUserBalanceDto.initialBalance())
                )
        );
    }

    @DeleteMapping(value = "/balance")
    ResponseEntity<Void> deleteBalance() {
        UUID userId = testUUID; //TODO
        coinManagementUseCase.deleteBalance(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addCoins(@Valid @RequestBody AddCoinsDto addCoinsDto) {
//        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        UUID userId = testUUID;
        coinManagementUseCase.addCoins(userId, addCoinsDto.coins(), addCoinsDto.type());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/deduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deductCoins(@Valid @RequestBody DeductCoinsDto deductCoinsDto) {
//        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
        UUID userId = testUUID;
        coinManagementUseCase.deductCoins(userId, deductCoinsDto.coins());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinTransactionsResponseDto> getCoinTransactions() {
        UUID userId = testUUID;
//        UUID userId = UUID.randomUUID(); //TODO get UUID form TOKEN
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
