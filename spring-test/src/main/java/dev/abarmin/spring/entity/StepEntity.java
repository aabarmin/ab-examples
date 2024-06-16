package dev.abarmin.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STEPS")
@ToString(exclude = "transaction")
@EqualsAndHashCode(exclude = "transaction")
public class StepEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    private TransactionEntity transaction;

    @Embedded
    private MoneyEntity amount;

    @Builder.Default
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();
}
