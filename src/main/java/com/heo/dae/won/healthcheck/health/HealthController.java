package com.heo.dae.won.healthcheck.health;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heo.dae.won.healthcheck.member.Member;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService){
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public List<Member> allMember(){
        List<Member> members = healthService.getAllMember();
        return members;
    }

    @PostMapping("/health")
    public Member insertMember(@RequestBody Member member){
        Member newMember = new Member();
        newMember.setUser(member.getUser());
        return healthService.save(newMember);
    }

    @PatchMapping("/health")
    public Member update(@RequestBody Member member){
        return healthService.update(member.getId(), member);
    }

    @GetMapping("/health/{id}/{code}")
    public void healthCheck(@PathVariable Long id, @PathVariable String code){

        Member member = healthService.getMember(id).orElseThrow();

        if(code.equals(member.getCode())){
            member.setToday(true);
            member.setLastCheckDate(LocalDate.now());

            healthService.update(id, member);
        }else{
            System.out.println("해당 id에 일치하지 않는 코드");
        }
    }

    /*
    * 앱에서 전달되는 응답
    * */
    @PostMapping("/response")
    public void responsePost(@RequestParam Map<String,String> request){
        System.out.println("payload >>> " );
        System.out.println("payload >> " + request.get("payload"));
        ObjectMapper mapper = new ObjectMapper();

        String str = request.get("payload");
        System.out.println("str >> " + str);

        try {
            Map<String,Object> result = mapper.readValue(str,Map.class);
            List<Map<String,Object>> actions = (List<Map<String, Object>>) result.get("actions");
            System.out.println("actions >>> " + actions.size());

            System.out.println("actions.get(0) >>> " + actions.get(0));

            System.out.println("actions.get(0).style >>> " + actions.get(0).get("style"));

            Map<String, String> user = (Map<String, String>) result.get("user");
            String userName = user.get("username");

            healthService.update(userName);

            System.out.println("호출한 username == " + userName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
