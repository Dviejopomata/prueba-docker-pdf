package com.roadto100k.pruebadocker;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class GithubRepository {
    public Map<String, Object> getRepository(
            String owner,
            String repo
    ) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<String> headersEntity = new HttpEntity<>(httpHeaders);
        ParameterizedTypeReference<Map<String, Object>> responseType =
                new ParameterizedTypeReference<Map<String, Object>>() {
                };
        ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(
                String.format("https://api.github.com/repos/%s/%s", owner, repo),
                HttpMethod.GET,
                headersEntity,
                responseType
        );
        return exchange.getBody();
    }
}
