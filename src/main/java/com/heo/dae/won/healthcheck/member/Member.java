package com.heo.dae.won.healthcheck.member;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String user;

    boolean today;
    boolean yesterday;

    String code;
    LocalDateTime updateDt;
    LocalDate lastCheckDate;
}
