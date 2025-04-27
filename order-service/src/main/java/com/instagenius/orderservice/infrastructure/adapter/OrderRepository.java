package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.OrderPersistencePort;
import com.instagenius.orderservice.domain.Order;
import com.instagenius.orderservice.infrastructure.exception.OrderNotFoundException;
import com.instagenius.orderservice.infrastructure.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderPersistencePort {
    private final JpaOrderRepository jpaOrderRepository;
    private static final String ORDER_NOT_FOUND_ERROR_MESSAGE = "Order not found!";

    @Override
    public Order save(Order order) {
        return OrderMapper.toOrder(
                jpaOrderRepository.save(
                        OrderMapper.toOrderEntity(order)
                )
        );
    }

    @Override
    public Order findOrderByUserIdAndReferenceId(UUID userId, String referenceId) {
        return OrderMapper.toOrder(
                jpaOrderRepository.findOrderByUserIdAndReferenceId(userId, referenceId)
                                  .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_ERROR_MESSAGE))
        );
    }

    @Override
    public Order findOrderByUserIdAndId(UUID userId, UUID id) {
        return OrderMapper.toOrder(jpaOrderRepository.findOrderByUserIdAndId(userId, id).orElseThrow(
                () -> new OrderNotFoundException(ORDER_NOT_FOUND_ERROR_MESSAGE)));
    }

    @Override
    public Order findOrderById(UUID id) {
        return OrderMapper.toOrder(jpaOrderRepository.findOrderById(id).orElseThrow(
                () -> new OrderNotFoundException(ORDER_NOT_FOUND_ERROR_MESSAGE)));
    }

    @Override
    public List<Order> findOrdersByUserId(UUID userId) {
        return jpaOrderRepository
                .findOrdersByUserId(userId)
                .stream()
                .map(OrderMapper::toOrder)
                .toList();
    }
}

@Repository
interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.userId = :userId")
    List<OrderEntity> findOrdersByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.userId = :userId AND o.referenceId = :referenceId")
    Optional<OrderEntity> findOrderByUserIdAndReferenceId(@Param("userId") UUID userId, @Param("referenceId") String referenceId);

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.userId = :userId AND o.id= :id")
    Optional<OrderEntity> findOrderByUserIdAndId(@Param("userId") UUID userId, @Param("id") UUID id);

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.id= :id")
    Optional<OrderEntity> findOrderById(@Param("id") UUID id);
}
