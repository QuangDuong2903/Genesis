package com.genesis.authservice;

import com.genesis.commons.persistence.AuditingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AuditingConfig.class)
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        userRepository.save(User.builder()
//                .username("quangduong")
//                .password(new BCryptPasswordEncoder().encode("292003"))
//                .balance(BigDecimal.valueOf(1000000))
//                .roles(Set.of(roleRepository.save(Role.builder()
//                                .name("Admin")
//                                .code("ADMIN")
//                                .build()),
//                        roleRepository.save(Role.builder()
//                                .name("User")
//                                .code("USER")
//                                .build())
//                ))
//                .build());
//    }
}
