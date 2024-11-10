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
    @Override
    public Order save(Order order) {
        return OrderMapper.toOrder(
                jpaOrderRepository.save(
                        OrderMapper.toOrderEntity(order)
                )
        );
    }

    @Override
    public Order findOrderByUserIdAndOrderId(UUID userId, UUID orderId) {
        return OrderMapper.toOrder(
                jpaOrderRepository.findOrderByUserIdAndId(userId, orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"))
        );
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

    @Query(value = "SELECT o FROM OrderEntity o WHERE o.userId = :userId AND o.id = :id")
    Optional<OrderEntity> findOrderByUserIdAndId(@Param("userId") UUID userId, @Param("id") UUID id);
}
