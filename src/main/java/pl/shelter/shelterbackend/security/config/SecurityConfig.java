package pl.shelter.shelterbackend.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.shelter.shelterbackend.user.UserService;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LogoutHandler logoutHandler;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("content-type", "Authorization", "Access-Control-Allow-Headers",
                "Access-Control-Request-Headers", "Access-Control-Request-Method"));
        //określa, które nagłówki są dozwolone w żądaniach do serwera. Taki nagłówek bedzie mogl byc przekazany w zadaniach
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        // definiuje nagłówki, które będą dostępne w odpowiedziach z serwera.Umożliwiam dostęp do tego nagłówka na froncie w odpowiedziach otrzymanych za pomocą Fetch API.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()       // wyłącza ochronę przed atakiem CSRF (Cross-Site Request Forgery)
                .authorizeRequests()    // rozpoczyna konfigurację autoryzacji żądań HTTP
                .antMatchers(
                        "/api/registration/register-admin",
                        "/animal/add-animal",
                        "/animal/delete-animal",
                        "/animal/edit-animal",
                        "/tab/add-about-entry",
                        "/tab/add-contact-entry",
                        "/tab/add-volunteering-entry"
                )
                .hasAuthority("ADMIN")
                .antMatchers(
                        "/api/adoptions/adoption-form-pdf",
                        "/api/adoptions/send-adoption-form"
                ).hasAuthority("USER")
                .antMatchers(
                        "/api/registration",
                        "/api/registration/confirm",
                        "/animal/animal-by-id",
                        "/animal/filtered-animals",
                        "/images/get-image-by-animalId",
                        "/auth/login",
                        "/tab/get-about-entry",
                        "/tab/get-contact-entry",
                        "/tab/get-volunteering-entry",
                        "/order/create-order",
                        "/oauth/create-oauth-token"
                        ).permitAll() //// definiuje, które żądania są dostępne bez autoryzacji
                .anyRequest().authenticated()
                .and()      /// kończy konfigurację autoryzacji i rozpoczyna konfigurację sesji
                .sessionManagement()    // definiuje zarządzanie sesją
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // definiuje politykę tworzenia sesji (tryb bezstanowy)
                //STATELESS - jesli uzytkownik jest zautentykowany i request jest zakonczony, to decision nie jest zapisywane
                .and()  // kończy konfigurację sesji i rozpoczyna konfigurację uwierzytelniania
                .authenticationProvider(authenticationProvider()) // definiuje sposób uwierzytelniania użytkowników
                //tutaj mowimy springowi - zrob filter zanim zautentykujesz uzytkownika
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)     // dodaje filtr uwierzytelniania JWT przed domyślnym filtrem uwierzytelniania opartym na loginie i haśle
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)       // tutaj jest zaimplementowany caly mechanizm logout
                //lambda expression -> co ma sie stac po tym jak wyloguje sie z sukcesem
                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
