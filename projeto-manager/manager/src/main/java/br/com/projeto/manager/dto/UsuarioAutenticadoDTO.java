package br.com.projeto.manager.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioAutenticadoDTO {
    private String login;
    private String token;
    private Boolean administrador;
    private Boolean autenticado;
}
