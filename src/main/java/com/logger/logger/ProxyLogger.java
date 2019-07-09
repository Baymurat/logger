package com.logger.logger;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
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
    public ResponseEntity mirrorRest(@RequestBody(required = false) String body, HttpMethod method, RequestEntity requestEntity) throws URISyntaxException
    {
        Logger logger = new Logger();
        String requestUri = requestEntity.getUrl().getPath();
        Pattern pattern = Pattern.compile("session/\\d+");
        Matcher matcher = pattern.matcher(requestUri);
        String sessionId = null;
        boolean requestForSession = false;

        if (requestUri.contains("session")) {
            if (matcher.find()) {
                sessionId = matcher.group(0).replaceAll("\\D+", "");
            } else {
                requestForSession = true;
            }
        } else {
            // request for productBase
            // admin/productBase
            // admin/logs
            // admin/sessionStats
            // trace/level
            // /health
            // /jobs
            // logs
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        URI uri = new URI("http", null, server, port, requestUri, requestEntity.getUrl().getQuery(), null);

        try {
            if (requestForSession) {
                responseEntity = restTemplate.exchange(uri, method, new HttpEntity<String>(body), String.class);
                JSONObject jsonObject = new JSONObject(responseEntity.getBody());
                sessionId = jsonObject.getString("sessionID");
                logger.log(requestEntity, sessionId);
                logger.log(responseEntity, sessionId);
            } else {
                logger.log(requestEntity, sessionId);
                responseEntity = restTemplate.exchange(uri, method, new HttpEntity<String>(body), String.class);
                logger.log(responseEntity, sessionId);
            }
        } catch (Exception e) {
            logger.log(e, sessionId);
        }

        return responseEntity;
    }
}
