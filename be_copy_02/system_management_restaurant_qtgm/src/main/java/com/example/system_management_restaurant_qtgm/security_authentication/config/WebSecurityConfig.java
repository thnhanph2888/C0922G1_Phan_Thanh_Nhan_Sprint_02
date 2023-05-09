package com.example.system_management_restaurant_qtgm.security_authentication.config;

import com.example.system_management_restaurant_qtgm.security_authentication.jwt.JwtFilter;
import com.example.system_management_restaurant_qtgm.security_authentication.service.AccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    enum Role {
        ADMIN("ADMIN"),
        EMPLOYEE("EMPLOYEE"),
        CUSTOMER("CUSTOMER");
        private final String role;

        private Role(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }
    @Autowired
    private AccountDetailService accountService;
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/food/create").hasAnyRole(Role.EMPLOYEE.getRole(), Role.ADMIN.getRole())
                .antMatchers("/api/food/**").permitAll()
                .antMatchers("/api/order/customer/**").hasAnyRole(Role.CUSTOMER.getRole(), Role.EMPLOYEE.getRole(), Role.ADMIN.getRole())
                .antMatchers("/api/order/employee/**").hasAnyRole(Role.EMPLOYEE.getRole(), Role.ADMIN.getRole())
                .antMatchers("/api/order/admin/**").hasRole(Role.ADMIN.getRole())
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.getRole())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
