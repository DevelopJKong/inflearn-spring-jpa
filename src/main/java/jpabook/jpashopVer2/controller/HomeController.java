package jpabook.jpashopVer2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/")
public class HomeController {

    @GetMapping(value = "/")
    public String home() {
        log.info("home controller 호출 시작:::");
        return "home";
    }
}
