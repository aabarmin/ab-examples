package dev.abarmin.spring.context.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "STEP")
public class Step {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private StepType type;

    @Embedded
    private AmountEntity amount;

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    private Transaction transaction;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
