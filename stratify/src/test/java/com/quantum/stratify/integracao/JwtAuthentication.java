package com.quantum.stratify.integracao;


import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.quantum.stratify.config.jwt.JwtToken;
import com.quantum.stratify.web.dtos.UsuarioLoginDto;

public class JwtAuthentication {
    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password){
        String token = client
                .post()
                .uri("/api/auth")
                .bodyValue(new UsuarioLoginDto(username, password ))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult()
                .getResponseBody()
                .getToken();
        return hearders -> hearders.add(HttpHeaders.AUTHORIZATION, "Bearer "+token);
    }
}
