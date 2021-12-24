package com.heo.dae.won.healthcheck.health;

import com.heo.dae.won.healthcheck.member.Member;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Service
public class HealthService {

    private final HealthRepository healthRepository;
    private Queue<String> queue = new LinkedList<>();

    public HealthService(HealthRepository healthRepository){
        this.healthRepository = healthRepository;
    }

    public List<Member> getAllMember(){
        return healthRepository.findAll();
    }

    public Optional<Member> getMember(Long id){
        return healthRepository.findById(id);
    }

    public Member save(Member member){
        return healthRepository.save(member);
    }

    public Member update(String user){

        Member mem = healthRepository.findByUser(user)
                .map(m ->{
                    m.setLastCheckDate(LocalDate.now());
                    m.setUpdateDt(LocalDateTime.now());
                    return healthRepository.save(m);
                }).orElse(null);

        return mem;
    }

    public Optional<Member> findByUser(String user){
        Optional<Member> mem = healthRepository.findByUser(user);

        return mem;
    }

    public Member update(Long id, Member member){
        Member mem = healthRepository.findById(id)
                .map(m ->{
                    m.setCode(member.getCode());
                    m.setLastCheckDate(member.getLastCheckDate());
                    m.setUser(member.getUser());
                    m.setUpdateDt(LocalDateTime.now());
                    return healthRepository.save(m);
                })
                .orElseGet(()->{
                    member.setId(id);
                    return healthRepository.save(member);
                });

        return mem;
    }

    public void getQueue(String id){
        queue.add(id);
        if(queue.size() >= 6){
            System.out.println("send");
        }else{
            System.out.println("no send");
        }
    }
}
