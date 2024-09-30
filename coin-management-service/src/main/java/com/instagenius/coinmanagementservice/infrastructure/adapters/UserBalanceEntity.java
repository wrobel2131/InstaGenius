package com.instagenius.coinmanagementservice.infrastructure.adapters;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_balance")
public class UserBalanceEntity {
    @I
}
