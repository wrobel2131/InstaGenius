package com.instagenius.coinmanagementservice.infrastructure.rest;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.infrastructure.dto.*;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinReservationMapper;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinTransactionMapper;
import com.instagenius.coinmanagementservice.infrastructure.mapper.UserBalanceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/coins")
@RequiredArgsConstructor
class CoinManagementController {
    private final CoinManagementUseCase coinManagementUseCase;
    private static final CoinTransactionMapper coinTransactionMapper = CoinTransactionMapper.INSTANCE;
    private static final UserBalanceMapper userBalanceMapper = UserBalanceMapper.INSTANCE;
    private static final CoinReservationMapper coinReservationMapper = CoinReservationMapper.INSTANCE;

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBalanceResponseDto> getBalance(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                userBalanceMapper.toUserBalanceResponseDto(
                        coinManagementUseCase.getBalance(userId)
                )
        );
    }

    @PostMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBalanceResponseDto> createBalance(@Valid @RequestBody CreateUserBalanceDto createUserBalanceDto,
                                                         @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                userBalanceMapper.toUserBalanceResponseDto(
                        coinManagementUseCase.createBalance(userId, createUserBalanceDto.initialBalance())
                )
        );
    }

    @DeleteMapping(value = "/balance")
    ResponseEntity<Void> deleteBalance(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.deleteBalance(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/reserve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinReservationDto> reserveCoins(@Valid @RequestBody ReserveCoinsDto reserveCoinsDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                coinReservationMapper.toCoinReservationDto(
                        coinManagementUseCase.reserveCoins(userId, reserveCoinsDto.amount(), reserveCoinsDto.operationId()
                        )
                )
        );
    }

    @PostMapping(value = "/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> completeReservation(@Valid @RequestBody CompleteReservationDto completeReservationDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.completeReservation(userId, completeReservationDto.reservationId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> cancelReservation(@Valid @RequestBody CancelReservationDto cancelReservationDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.cancelReservation(userId, cancelReservationDto.reservationId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addCoins(@Valid @RequestBody AddCoinsDto addCoinsDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.addCoins(userId, addCoinsDto.coins(), addCoinsDto.type());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinTransactionsResponseDto> getCoinTransactions(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                new CoinTransactionsResponseDto(
                        coinManagementUseCase.getCoinTransactions(userId)
                                .stream()
                                .map(coinTransactionMapper::toCoinTransactionResponseDto)
                                .toList()
                )
        );
    }

    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
