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
@RequestMapping("/api/v1/coins/")
@RequiredArgsConstructor
class CoinManagementController {
    private final CoinManagementUseCase coinManagementUseCase;
    private static final CoinTransactionMapper coinTransactionMapper = CoinTransactionMapper.INSTANCE;
    private static final UserBalanceMapper userBalanceMapper = UserBalanceMapper.INSTANCE;
    private static final CoinReservationMapper coinReservationMapper = CoinReservationMapper.INSTANCE;

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserBalanceResponseDto> getBalance(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Get Balance endpoint");
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
        System.out.println("Create Balance endpoint");
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                userBalanceMapper.toUserBalanceResponseDto(
                        coinManagementUseCase.createBalance(userId, createUserBalanceDto.initialBalance())
                )
        );
    }

    @DeleteMapping(value = "/balance")
    ResponseEntity<Void> deleteBalance(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Delete Balance endpoint");
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.deleteBalance(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/reserve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinReservationResponseDto> reserveCoins(@Valid @RequestBody ReserveCoinsDto reserveCoinsDto, @AuthenticationPrincipal Jwt jwt) {
        System.out.println("Reserve Coins endpoint");
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                coinReservationMapper.toCoinReservationDto(
                        coinManagementUseCase.reserveCoins(userId, reserveCoinsDto.amount(),
                                                           reserveCoinsDto.operationId()
                        )
                )
        );
    }

    @PostMapping(value = "/complete/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> completeReservation(@PathVariable("reservationId") UUID reservationId, @AuthenticationPrincipal Jwt jwt) {
        System.out.println("Complete Reservation endpoint");
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.completeReservation(userId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/cancel/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> cancelReservation(
            @PathVariable("reservationId") UUID reservationId, @AuthenticationPrincipal Jwt jwt) {
        System.out.println("Cancel Reservation endpoint");
        UUID userId = getUserUUIDFromJwtToken(jwt);
        coinManagementUseCase.cancelReservation(userId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{userId}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addCoins(@PathVariable("userId") UUID userId, @Valid @RequestBody AddCoinsDto addCoinsDto) {
        System.out.println("Add Coins endpoint");
        coinManagementUseCase.addCoins(userId, addCoinsDto.coins(), addCoinsDto.type());

        System.out.println("Added Coins");
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinTransactionsResponseDto> getCoinTransactions(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("Get Coins Transactions endpoint");
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
