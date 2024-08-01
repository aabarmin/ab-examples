package dev.abarmin.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private String username;

    private String password;

    @JoinColumn(name = "username")
    @OneToMany(fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities = new ArrayList<>();
}
