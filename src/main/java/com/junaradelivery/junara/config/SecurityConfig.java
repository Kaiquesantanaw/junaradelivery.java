package com.junaradelivery.junara.config;

import com.junaradelivery.junara.service.LoginAttemptService;
import com.junaradelivery.junara.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, UserService userService,
                        PasswordEncoder passwordEncoder,
                        LoginAttemptService loginAttemptService)
                        throws Exception {
                http
                                // CSRF: ativo para rotas web, desativado apenas para API REST
                                .csrf(csrf -> csrf
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                                                .ignoringRequestMatchers("/api/**"))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login").permitAll()
                                                // /register só acessível por admin autenticado
                                                .requestMatchers("/register").authenticated()
                                                // H2 console requer autenticação
                                                .requestMatchers("/h2-console/**").authenticated()
                                                // API pública de leitura (cardápio) e criação de pedidos (loja)
                                                .requestMatchers(HttpMethod.GET, "/api/v1/produtos",
                                                                "/api/v1/produtos/*")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/v1/pedidos").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/", true)
                                                .successHandler(loginSuccessHandler(loginAttemptService))
                                                .failureHandler(loginFailureHandler(loginAttemptService))
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                // Bloquear clickjacking
                                .headers(headers -> headers
                                                .frameOptions(fo -> fo.sameOrigin()));

                return http.build();
        }

        private AuthenticationSuccessHandler loginSuccessHandler(LoginAttemptService loginAttemptService) {
                SimpleUrlAuthenticationSuccessHandler delegate = new SimpleUrlAuthenticationSuccessHandler("/");
                return (request, response, authentication) -> {
                        loginAttemptService.registerSuccess(getClientIP(request));
                        delegate.onAuthenticationSuccess(request, response, authentication);
                };
        }

        private AuthenticationFailureHandler loginFailureHandler(LoginAttemptService loginAttemptService) {
                SimpleUrlAuthenticationFailureHandler delegate = new SimpleUrlAuthenticationFailureHandler(
                                "/login?error");
                return (request, response, exception) -> {
                        loginAttemptService.registerFailure(getClientIP(request));
                        // If now blocked, forward to specific error
                        if (loginAttemptService.isBlocked(getClientIP(request))) {
                                response.sendRedirect("/login?blocked");
                        } else {
                                delegate.onAuthenticationFailure(request, response, exception);
                        }
                };
        }

        private String getClientIP(HttpServletRequest request) {
                String forwarded = request.getHeader("X-Forwarded-For");
                if (forwarded != null && !forwarded.isBlank()) {
                        return forwarded.split(",")[0].trim();
                }
                return request.getRemoteAddr();
        }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                return web -> web.ignoring()
                                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**");
        }
}
