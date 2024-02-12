package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvatarControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AvatarController avatarController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoadsTest() throws Exception {
        Assertions.assertThat(avatarController).isNotNull();
    }

    @Test
    public void findAllTest() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/avatars/?page=1&size=2", Collection.class).size())
                .isEqualTo(0);
    }
}
