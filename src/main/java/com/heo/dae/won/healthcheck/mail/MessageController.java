package com.heo.dae.won.healthcheck.mail;

import com.heo.dae.won.healthcheck.health.HealthService;
import com.heo.dae.won.healthcheck.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Documented;
import java.time.LocalDate;

@RestController
public class MessageController {

    private final RestTemplate restTemplate;
    private final HealthService healthService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageController(RestTemplateBuilder builder
                            ,HealthService healthService){
        this.restTemplate = builder.build();
        this.healthService = healthService;
    }

    /*
    * 호출하기전 이전 호출과 현재사이에서 응답이 있었는지 확인
    * */
    @GetMapping("/send")
//    @Scheduled(cron = "0 0 * * *")
    public HttpStatus send(){
        String url = "http://localhost:8090/block";

        RequestEntity request = RequestEntity
                .get(url).build();

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        return response.getStatusCode();
    }

    @GetMapping("/datetest")
    public void checkHealth(@RequestParam String user){
        Member mem = healthService.findByUser(user).orElse(null);

        if(mem == null){
            logger.info("mem is null");
        }else{
            System.out.println("memdate == " + mem.getLastCheckDate());

            LocalDate before1Day = LocalDate.now().minusDays(1);
            logger.info("before1Day == " + before1Day);

            if(before1Day.compareTo(mem.getLastCheckDate()) >= 0){
                logger.info("date equals");
            }else{
                logger.info("send alert message");
            }
        }
    }
}
