package pl.akademiaspring.security_week_01.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        val userAdmin: User = User(
                "Kamil",
                getPasswordEncoder().encode("123"),
                Collections.singleton(SimpleGrantedAuthority("ROLE_ADMIN")))
        val userUser: User = User(
                "user",
                getPasswordEncoder().encode("321"),
                Collections.singleton(SimpleGrantedAuthority("ROLE_USER")))
        auth!!.inMemoryAuthentication().apply {
            withUser(userAdmin)
            withUser(userUser)
        }
    }

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .antMatchers("/hello/forAdmin").hasRole("ADMIN")
                .antMatchers("/hello/forUser").hasAnyRole("ADMIN", "USER")
                .antMatchers("/hello/forUnknown").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().logoutSuccessUrl("/hello/bye")

    }
}