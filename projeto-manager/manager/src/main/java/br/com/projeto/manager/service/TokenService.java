package br.com.projeto.manager.service;

import br.com.projeto.manager.entity.Token;
import br.com.projeto.manager.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/* *
*     SERVISOS TOKEN: CRIAÇÃO DE TOKEN, RENOVAÇÃO DE TOKEN E VERIFICAÇÃO DE TOKEN
* */

@Service
public class TokenService {
    public static final int TOKEN_EXPIRACAO = 300_000;

    /* Não é correto deixar esta senha dos scripts */
    public static final String TOKEN_SENHA = "72bea833-740f-45d7-9400-8ebb31488dce";
    public Token gerarToken(Optional<Usuario> usuario) {

        var date = new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO);
        Timestamp expiracao =new Timestamp(date.getTime());
        var token =  JWT.create().
                withSubject(usuario.get().getNome())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC512(TOKEN_SENHA));

        return Token.builder()
                .token(token)
                .login(usuario.get().getLogin())
                .administrador(usuario.get().getAdministrador())
                .expiracao(expiracao)
                .build();
    }

    public Boolean getRenova(Optional<Usuario> usuario, Optional<Token> tokenEntity) {

        var date = new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO);
        Timestamp expiracao =new Timestamp(date.getTime());

        String novoToken = JWT.create().
                withSubject(usuario.get().getNome())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC512(TOKEN_SENHA));

        if(novoToken.isEmpty()){
            return false;
        }

        tokenEntity.get().setToken(novoToken);
        tokenEntity.get().setExpiracao(expiracao);

        return true;
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(TOKEN_SENHA))
                .build().verify(token).getSubject();

    }
}
