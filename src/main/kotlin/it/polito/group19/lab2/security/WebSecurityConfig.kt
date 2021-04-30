package it.polito.group19.lab2.security

import it.polito.group19.lab2.services.UserDetailsService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig (val passwordEncoder: PasswordEncoder, val userDetailsService: UserDetailsService, val authenticationEntryPoint: AuthenticationEntryPoint, val jwtUtils: JwtUtils): WebSecurityConfigurerAdapter(){

    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)

    /*.inMemoryAuthentication()
            .withUser("root")
            .password(passwordEncoder.encode("admin"))
            .roles("user")*/
    }

    override fun configure(http: HttpSecurity) {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
//            .addFilterBefore(JwtAuthenticationTokenFilter(jwtUtils), UsernamePasswordAuthenticationFilter::class.java)
            .csrf().disable()
    }

}