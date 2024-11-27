package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.UserPort;
import com.instagenius.orderservice.domain.UserProfile;
import com.instagenius.orderservice.infrastructure.exception.CoinManagementException;
import com.instagenius.orderservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.orderservice.infrastructure.mapper.UserRelatedMapper;
import com.instagenius.orderservice.infrastructure.rest.UserClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserPort {
    private final UserClient userClient;
    private static final UserRelatedMapper userRelatedMapper = UserRelatedMapper.INSTANCE;

    @Override
    public UserProfile getUserProfile(UUID userId) {
        try {
            System.out.println("Getting user profle");
            return userRelatedMapper.toUserProfile(
                    userClient.getUserProfile(userId)
            );
        } catch (FeignException e) {
            System.out.println("Exception occurred while getting user profile from user service");
            e.printStackTrace();
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
