package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.domain.UserBalance;
import com.instagenius.coinmanagementservice.infrastructure.exception.UserNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.mapper.UserBalanceMapper;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserBalanceRepository implements UserBalancePersistencePort {
    private final JpaUserBalanceRepository jpaUserBalanceRepository;
    private static final UserBalanceMapper userBalanceMapper = UserBalanceMapper.INSTANCE;

    @Override
    public UserBalance findUserBalanceByUserId(UUID userId) {
        return userBalanceMapper.toUserBalance(
                jpaUserBalanceRepository.findUserBalanceEntityByUserId(userId)
                        .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"))
        );
    }

    @Override
    public UserBalance save(UserBalance userBalance) {
        UserBalanceEntity userBalanceEntity = userBalanceMapper.toUserBalanceEntity(userBalance);
        System.out.println("Created user balance entity: "+userBalanceEntity);

        UserBalanceEntity savedUserBalanceEntity = jpaUserBalanceRepository.save(userBalanceEntity);
        System.out.println("Saved user balance entity: "+savedUserBalanceEntity);
        UserBalance savedUserBalance = userBalanceMapper.toUserBalance(savedUserBalanceEntity);
        System.out.println("Saved user balance: " + savedUserBalance);
        return savedUserBalance;
//        return userBalanceMapper.toUserBalance(
//                jpaUserBalanceRepository.save(userBalanceMapper.toUserBalanceEntity(userBalance))
//        );
    }
}

@Repository
interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {
    Optional<UserBalanceEntity> findUserBalanceEntityByUserId(UUID userId);
}
