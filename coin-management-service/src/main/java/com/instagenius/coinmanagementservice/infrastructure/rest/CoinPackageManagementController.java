package com.instagenius.coinmanagementservice.infrastructure.rest;

import com.instagenius.coinmanagementservice.application.CoinPackageManagementUseCase;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinPackageDto;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinPackagesResponseDto;
import com.instagenius.coinmanagementservice.infrastructure.dto.CreateCoinPackageRequestDto;
import com.instagenius.coinmanagementservice.infrastructure.dto.UpdateCoinPackageRequestDto;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinPackageMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coin-packages")
public class CoinPackageManagementController {
    private final CoinPackageManagementUseCase coinPackageManagementUseCase;
    private static final CoinPackageMapper coinPackageMapper = CoinPackageMapper.INSTANCE;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinPackagesResponseDto> getCoinPackages() {
        System.out.println("Get Coins Package endpoint");
        return ResponseEntity.ok(
                new CoinPackagesResponseDto(
                        coinPackageManagementUseCase
                                .getCoinPackages()
                                .stream()
                                .map(coinPackageMapper::toCoinPackageDto)
                                .toList())
        );
    }

    @GetMapping(value = "/{coinPackageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinPackageDto> getCoinPackage(
            @PathVariable("coinPackageId") UUID coinPackageId) {
        System.out.println("Get Coins Package endpoint");
        return ResponseEntity.ok(coinPackageMapper.toCoinPackageDto(
                coinPackageManagementUseCase.getCoinPackage(coinPackageId))
        );
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinPackageDto> createCoinPackage(
            @RequestBody @Valid CreateCoinPackageRequestDto createCoinPackageRequestDto) {
        System.out.println("Create Coins Package endpoint");

        return ResponseEntity.ok(
                coinPackageMapper.toCoinPackageDto(
                        coinPackageManagementUseCase.createCoinPackage(createCoinPackageRequestDto.name(),
                                                                       createCoinPackageRequestDto.description(),
                                                                       createCoinPackageRequestDto.coinAmount(),
                                                                       createCoinPackageRequestDto.price(),
                                                                       createCoinPackageRequestDto.currency())
                )
        );
    }

    @PutMapping(value = "/{coinPackageId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateCoinPackage(
            @PathVariable("coinPackageId") UUID coinPackageId,
            @RequestBody @Valid UpdateCoinPackageRequestDto updateCoinPackageRequestDto) {
        System.out.println("Update Coins Package endpoint");

        coinPackageManagementUseCase.updateCoinPackage(coinPackageId, updateCoinPackageRequestDto.name(),
                                                       updateCoinPackageRequestDto.description(),
                                                       updateCoinPackageRequestDto.coinAmount(),
                                                       updateCoinPackageRequestDto.price(),
                                                       updateCoinPackageRequestDto.currency(),
                                                       updateCoinPackageRequestDto.isActive());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{coin-package-id}")
    ResponseEntity<Void> deleteCoinPackage(
            @PathVariable("coin-package-id") UUID coinPackageId) {
        System.out.println("Delete Coins Package endpoint");
        coinPackageManagementUseCase.deleteCoinPackage(coinPackageId);
        return ResponseEntity.noContent().build();
    }
}
