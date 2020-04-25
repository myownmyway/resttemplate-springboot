package com.wpw.resttemplatespringboot.controller;

import com.wpw.resttemplatespringboot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author wpw
 */
@RestController
@Slf4j
public class UserController {
    private final RestTemplate restTemplate;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/get")
    public User getUserInfo(@RequestParam(value = "id") Long id) {
        Map<String, Long> map = new HashMap<>(16, 0.75f);
        map.put("id", id);
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity("http://localhost:8080/get?id={id}", User.class, map);
        return Optional.ofNullable(userResponseEntity.getBody()).orElse(null);
    }

    @GetMapping(value = "/list")
    public List<User> listUser() {
        ResponseEntity<List> listResponseEntity = restTemplate.getForEntity("http://localhost:8080/listUser", List.class);
        log.info("状态码:{}", listResponseEntity.getStatusCode());
        log.info("数据信息:{}", listResponseEntity.getBody());
        return Optional.ofNullable(listResponseEntity.getBody()).orElse(new ArrayList());
    }

    @PostMapping(value = "/saveUser")
    public Long saveUser(@RequestBody User user) {
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<Long> postForEntity = restTemplate.postForEntity("http://localhost:8080/save", user, Long.class);
        log.info("状态码:{}", postForEntity.getStatusCode());
        log.info("数据信息:{}", postForEntity.getBody());
        return postForEntity.getBody();
    }
}
