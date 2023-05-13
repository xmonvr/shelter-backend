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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.shelter.shelterbackend.user.AppUserService;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));       //todo
        configuration.setAllowedHeaders(List.of("content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()       // wyłącza ochronę przed atakiem CSRF (Cross-Site Request Forgery)
                .authorizeRequests()    // rozpoczyna konfigurację autoryzacji żądań HTTP
                .antMatchers("/**/auth/**", "/api/registration/**").permitAll() //// definiuje, które żądania są dostępne bez autoryzacji
                .anyRequest().authenticated()       // definiuje, że wszystkie pozostałe żądania muszą być uwierzytelnione (zalogowany użytkownik)
                .and()      /// kończy konfigurację autoryzacji i rozpoczyna konfigurację sesji
                .sessionManagement()    // definiuje zarządzanie sesją
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // definiuje politykę tworzenia sesji (tryb bezstanowy)
                //STATELESS - jesli uzytkownik jest zautentykowany i request jest zakonczony, to decision nie jest zapisywane
                .and()  // kończy konfigurację sesji i rozpoczyna konfigurację uwierzytelniania
                .authenticationProvider(authenticationProvider()) // definiuje sposób uwierzytelniania użytkowników
                //tutaj mowimy springowi - zrob filter zanim zautentykujesz uzytkownika
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);     // dodaje filtr uwierzytelniania JWT przed domyślnym filtrem uwierzytelniania opartym na loginie i haśle
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(appUserService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
