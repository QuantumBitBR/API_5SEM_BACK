package com.quantum.stratify.integracao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.quantum.stratify.web.dtos.PercentualStatusUsuarioDTO;
import com.quantum.stratify.web.dtos.TotalCardsDTO;
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"resources/sql/userStories/delete.sql", "resources/sql/userStories/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "resources/sql/userStories/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserStoryIT {

    @Autowired
    WebTestClient testClient;

     @Test
    void getTotalCards_status200(){
        TotalCardsDTO responseBody = testClient.get()
                .uri("/userStory/total-cards")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TotalCardsDTO.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        if (responseBody != null) {
            org.assertj.core.api.Assertions.assertThat(responseBody.quantidadeUserStories()).isEqualTo(1);
        }


    }

    @Test
    void getPercentualStatus_status200(){
        List<PercentualStatusUsuarioDTO> responseBody = testClient.get()
                .uri("/userStory/percentual-por-status")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PercentualStatusUsuarioDTO.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        if (responseBody != null) {
            org.assertj.core.api.Assertions.assertThat(responseBody).hasSize(1);
        }
    }

    
}
