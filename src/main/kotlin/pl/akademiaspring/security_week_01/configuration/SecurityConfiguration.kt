package pl.akademiaspring.security_week_01.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import pl.akademiaspring.security_week_01.Roles
import java.util.*

@Configuration
class SecurityConfiguration(
        private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun getPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)
    }

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .antMatchers("/hello/forAdmin").hasRole(Roles.ADMIN.name)
                .antMatchers("/hello/forUser").hasAnyRole(Roles.ADMIN.name, Roles.USER.name)
                .antMatchers("/hello/forUnknown").permitAll()
                .antMatchers("/sign-up").not().authenticated()
                .antMatchers("/verify-token").not().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/hello/forUser")
                .failureForwardUrl("/hello/forUnknown")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/hello/bye")
                .and()
                .rememberMe().rememberMeParameter("remember")
    }
}