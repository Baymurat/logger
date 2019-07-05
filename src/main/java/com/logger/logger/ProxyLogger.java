package com.logger.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ProxyLogger {

    private static final Logger logger = LoggerFactory.getLogger(ProxyLogger.class);
    private String server = "localhost";
    private int port = 8000;

    @RequestMapping("/**")
    @ResponseBody
    public ResponseEntity mirrorRest(@RequestBody(required = false) String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException
    {
        System.out.println(request.getRequestURI() + request.getQueryString());
        logger.info("REQUEST {}", request);
        logger.info("BODY {}", body);
        URI uri = new URI("http", null, server, port, request.getRequestURI(), request.getQueryString(), null);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, method, new HttpEntity<String>(body), String.class);

        StringBuilder builder = new StringBuilder();
        builder.append("");
        //return responseEntity.getBody();
        return responseEntity;
    }
}
