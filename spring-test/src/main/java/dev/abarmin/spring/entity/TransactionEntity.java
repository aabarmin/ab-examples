package dev.abarmin.spring.entity;

import dev.abarmin.spring.model.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTIONS")
public class TransactionEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_FROM")
    private long accountFrom;

    @Column(name = "ACCOUNT_TO")
    private long accountTo;

    @Column(name = "TRANSACTION_STATUS")
    private TransactionStatus status;

    @Embedded
    private MoneyEntity amount;
}
