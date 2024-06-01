package dev.abarmin.spring.context.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "FROM_ID")
    private long fromId;

    @Column(name = "TO_ID")
    private long toId;

    @Embedded
    private AmountEntity amount;

    @OneToMany(mappedBy = "transaction")
    private Set<Step> steps = new HashSet<>();

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
