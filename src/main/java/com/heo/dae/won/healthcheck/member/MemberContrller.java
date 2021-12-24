package com.heo.dae.won.healthcheck.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberContrller {

    @GetMapping("/member/{id}")
    public void getMember(@PathVariable String id){
        System.out.println("member id == " + id);
    }
}
