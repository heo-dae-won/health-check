package com.heo.dae.won.healthcheck.code;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class CodeService {

    public String createNo(int size) {
        String code = "";
        Random random = new Random();

        for(int i = 0 ; i < size; i++){
            char ch = (char)((random.nextInt(26)) + 65);
            code += String.valueOf(ch);
        }

        return code;
    }
}
