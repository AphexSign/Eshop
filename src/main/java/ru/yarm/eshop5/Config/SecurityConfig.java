package ru.yarm.eshop5.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.yarm.eshop5.Models.Role;
import ru.yarm.eshop5.Services.SecurityUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserDetailsService securityUserDetailsService;

    public SecurityConfig(SecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/login","/auth/registration","/error","/products").permitAll()
                .antMatchers("/users","/prod_admin","/order_admin","/category_admin").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name())
                .antMatchers("/cart","/orders").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name(), Role.CLIENT.name())
                .antMatchers("/hello").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name(), Role.CLIENT.name(), Role.BANNED.name())
                .antMatchers("/banned").hasAnyAuthority(Role.BANNED.name())
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .failureUrl("/auth/login?error")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello",true)
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/auth/login").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();
    }


    //Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //Добавили шифрование паролей в СВЕРКУ, иначе не было
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(getPasswordEncoder());


        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .authorities("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .authorities("ADMIN")
                .and()
                .withUser("manager")
                .password("manager")
                .authorities("MANAGER");





    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
