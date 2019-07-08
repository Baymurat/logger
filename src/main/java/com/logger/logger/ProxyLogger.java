package com.logger.logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ProxyLogger {

    private String server = "localhost";
    private int port = 8000;

    @RequestMapping("/**")
    @ResponseBody
    public ResponseEntity mirrorRest(@RequestBody(required = false) String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException
    {
        Logger logger = new Logger();
        String requestUri = request.getRequestURI();
        Pattern pattern = Pattern.compile("session/\\d+");
        Matcher matcher = pattern.matcher(requestUri);
        String sessionId = null;

        if (matcher.find()) {
            sessionId = matcher.group(0).replace("\\D+", "");

            logger.log(request.getMethod(), sessionId);
            logger.log(request.getRequestURI(), sessionId);
            logger.log(request.getQueryString(), sessionId);
        } else {
            logger.log(request.getMethod(), sessionId);
            logger.log(request.getRequestURI(), sessionId);
            logger.log(request.getQueryString(), sessionId);
        }

        //System.out.println(request.getRequestURI() + request.getQueryString());
        URI uri = new URI("http", null, server, port, request.getRequestURI(), request.getQueryString(), null);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(uri, method, new HttpEntity<String>(body), String.class);
        } catch (Exception e) {
            logger.log(e, sessionId);
        }

        //return responseEntity.getBody();
        return responseEntity;
    }
}
