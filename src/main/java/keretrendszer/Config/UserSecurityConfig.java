package keretrendszer.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserSecurityConfig{
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(csrf-> csrf.disable())
                .authorizeRequests(auth -> {
                auth.antMatchers("/").permitAll();
                auth.antMatchers("/user").hasRole("user");
                auth.antMatchers("/stacker").hasRole("stacker");
                auth.antMatchers("/admin").hasRole("admin");
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
