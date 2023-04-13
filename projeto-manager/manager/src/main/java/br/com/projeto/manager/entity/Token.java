package br.com.projeto.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "expiracao", nullable = false)
    private Timestamp expiracao;

    @Column(name = "administrador", nullable = false)
    private Boolean administrador;
}
