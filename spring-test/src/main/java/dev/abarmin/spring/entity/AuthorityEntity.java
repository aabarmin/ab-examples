package dev.abarmin.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(AuthorityId.class)
@Table(name = "authorities")
public class AuthorityEntity {
    @Id
    private String username;

    @Id
    private String authority;
}
