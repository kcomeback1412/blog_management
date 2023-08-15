package fa.training.config;

import fa.training.auth.CustomUsersDetailService;
import fa.training.entities.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomUsersDetailService usersDetailService;

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(usersDetailService).passwordEncoder(passwordEncoder);
    }

    private static final String[] permitAllLink = {
        "/",
        "/home",
        "/",
        "/about",
        "/login",
        "/register",
        "/contact",
        "/js/**",
        "/css/**",
        "/api/v1/profile"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers(permitAllLink).permitAll();

            auth.requestMatchers("/home/**")
                .hasRole(RoleEnum.USER.getName())
                .anyRequest().authenticated();
        }).formLogin(form -> {
            form.loginPage("/login")
                .loginProcessingUrl("/login-check")
                .defaultSuccessUrl("/home")
                .permitAll();
        });
        return httpSecurity.build();
    }
}
