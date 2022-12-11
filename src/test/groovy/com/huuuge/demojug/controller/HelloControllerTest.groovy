package com.huuuge.demojug.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration
class HelloControllerTest extends Specification {

    @Autowired(required = false)
    private HelloController helloController

    @Autowired
    WebTestClient webClient

    static final String GREETING_URI = "/greeting"
    static final String TEST_GREETING = "Welcome in test!"

    def "should load context with all expected beans"() {
        expect: "the HelloController is created"
        helloController
    }

    def "should return configured greeting"() {
        when: "call for greetings is made"
        def responseSpec = webClient.get()
                .uri(GREETING_URI)
                .exchange()

        then: "test greeting returned"
        responseSpec.expectBody(String.class).isEqualTo(TEST_GREETING)
    }

    def "should return greeting for requested city"() {
        given:
        def requestedCity = "Elk"

        when: "call for greetings is made"
        def responseSpec = webClient.get()
                .uri(GREETING_URI + "/" + requestedCity)
                .exchange()

        then: "test greeting returned"
        responseSpec.expectBody(String.class).isEqualTo("Hello " + requestedCity + "!")
    }

    def "should return bad request for city not matching expected size"() {
        when: "call for greetings is made"
        def responseSpec = webClient.get()
                .uri(GREETING_URI + "/" + requestedCity)
                .exchange()

        then: "validation error occur"
        responseSpec.expectStatus().isBadRequest()

        where:
        requestedCity << ["12", "parametrizations"]
    }

}