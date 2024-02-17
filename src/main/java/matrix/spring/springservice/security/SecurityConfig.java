package matrix.spring.springservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/api/v1/admin/.*").hasRole("1")
                .requestMatchers(HttpMethod.POST, "/api/v1/admin/.*").hasRole("1")
                .requestMatchers(HttpMethod.PUT, "/api/v1/admin/.*").hasRole("1")
                .requestMatchers(HttpMethod.GET, "/api/v1/user/.*").hasAnyRole("0", "1")
                .requestMatchers(HttpMethod.POST, "/api/v1/user/.*").hasAnyRole("0", "1")
                .requestMatchers(HttpMethod.PUT, "/api/v1/user/.*").hasAnyRole("0", "1")
                .anyRequest().authenticated();
    }
}
