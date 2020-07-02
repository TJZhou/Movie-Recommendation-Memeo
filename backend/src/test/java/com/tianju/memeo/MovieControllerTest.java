package com.tianju.memeo;

import com.tianju.memeo.model.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MemeoApplication.class})
public class MovieControllerTest {
    @Value("${auth0.test.access-token}")
    private String testAccessToken;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void visitApiWithoutToken() {
        ResponseEntity<Response> resp =
                testRestTemplate.exchange("/movie/10000", HttpMethod.GET, null, Response.class);
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void visitApiWithInvalidToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer this-is-a-invalid-token");
        ResponseEntity<Response> resp =
                testRestTemplate.exchange("/movie/10000", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), Response.class);
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    // @Test
    // public void visitUnknownApi() {
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     httpHeaders.add("Authorization", "Bearer " + testAccessToken);
    //     ResponseEntity<Response> resp =
    //             testRestTemplate.exchange("/invalid-api/invalid-api", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), Response.class);
    //     Assert.assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    // }

    // @Test
    // public void visitApiWithToken() {
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     httpHeaders.add("Authorization", "Bearer " + testAccessToken);
    //     ResponseEntity<Response> resp =
    //             testRestTemplate.exchange("/movie/10000", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), Response.class);
    //     Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
    // }
}
