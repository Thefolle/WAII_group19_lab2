package it.polito.group19.lab2.security

import it.polito.group19.lab2.services.UserDetailsService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class WebSecurityConfig (val passwordEncoder: PasswordEncoder, val userDetailsService: UserDetailsService, val authenticationEntryPoint: AuthenticationEntryPoint, val jwtUtils: JwtUtils): WebSecurityConfigurerAdapter(){

    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)

    /*authenticationManagerBuilder.inMemoryAuthentication()
            .withUser("root")
            .password(passwordEncoder.encode("admin"))
            .roles("user")*/
    }

    override fun configure(http: HttpSecurity) {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests().antMatchers("/").permitAll()

        http.authorizeRequests().antMatchers("/auth/**").permitAll()

        http.authorizeRequests().antMatchers("/wallets/**").authenticated()

        //http.formLogin().permitAll().and().logout().permitAll()

        http.csrf().disable()

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

        http.addFilterBefore(JwtAuthenticationTokenFilter(jwtUtils), UsernamePasswordAuthenticationFilter::class.java)





//        http.sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//                .authorizeRequests()
//                .antMatchers("/wallets/**")
//                .authenticated()
//                .and()
//
//                .authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
//            //.anyRequest().authenticated()
//            .and()
//                .authorizeRequests()
//                .antMatchers("/login", "/logout")
//                .permitAll()
//                .and()
//            //.formLogin()
//            //.disable()
//            .exceptionHandling()
//            .authenticationEntryPoint(authenticationEntryPoint)
//            .and()
//            .addFilterBefore(JwtAuthenticationTokenFilter(jwtUtils), UsernamePasswordAuthenticationFilter::class.java)
//            .csrf().disable()
    }

}
