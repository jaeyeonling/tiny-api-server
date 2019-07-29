package com.jaeyeonling.tiny.domain.health;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping(path = "/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() { }
}
