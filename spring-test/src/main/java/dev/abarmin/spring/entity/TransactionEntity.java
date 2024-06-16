package dev.abarmin.spring.entity;

import dev.abarmin.spring.model.TransactionStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTIONS")
@EqualsAndHashCode(exclude = "steps")
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

    @Builder.Default
    @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StepEntity> steps = new HashSet<>();

    @Embedded
    private MoneyEntity amount;

    @Builder.Default
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();
}
