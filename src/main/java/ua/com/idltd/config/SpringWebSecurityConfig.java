package ua.com.idltd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SpringWebSecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository());
        http
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/login-success")
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .antMatchers("/index.html").permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403.html");
//                    .accessDeniedHandler(accessDeniedHandler());
    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(oracleUserDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder());
//    }

//protected AccessDeniedHandler accessDeniedHandler() {
//    return new AccessDeniedHandler() {
//        @Override
//        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//            response.getWriter().append("Access denied");
//            response.setStatus(403);
//        }
//    };
//}

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

//        auth.userDetailsService(oracleUserDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder());

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT user_username username, user_password password, user_enabled enabled FROM users WHERE user_username = ?")
                .authoritiesByUsernameQuery("SELECT user_username username, rol_role role FROM users u, roles r, user_roles ur" +
                        " WHERE u.user_username = ? and ur.USER_ID = u.USER_ID and r.ROL_ID = ur.ROL_ID")
                .passwordEncoder(bCryptPasswordEncoder())
                ;
//        auth
//                .inMemoryAuthentication()
//                .withUser("jim").password("{noop}demo").roles("ADMIN").and()
//                .withUser("bob").password("{noop}demo").roles("USER").and()
//                .withUser("ted").password("{noop}demo").roles("USER","ADMIN");
    }

}

