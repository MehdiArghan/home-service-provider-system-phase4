package com.example.homeserviceprovidersystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    final UserDetailsServiceImpl userDetailsService;
    final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/admin/authenticate").permitAll()
                        .requestMatchers("/admin/addDuty").hasAuthority("ADMIN")
                        .requestMatchers("/admin/addSubDuty").hasAuthority("ADMIN")
                        .requestMatchers("/admin/editDescriptionSubDuty").hasAuthority("ADMIN")
                        .requestMatchers("/admin/editBasePriceSubDuty").hasAuthority("ADMIN")
                        .requestMatchers("/admin/expertConfirmation").hasAuthority("ADMIN")
                        .requestMatchers("/admin/changePassword").hasAuthority("ADMIN")
                        .requestMatchers("/admin/findAllDuty").hasAuthority("ADMIN")
                        .requestMatchers("/admin/findAllSubDuty").hasAuthority("ADMIN")
                        .requestMatchers("/admin/findAllDisableExperts").hasAuthority("ADMIN")
                        .requestMatchers("/admin/searchCustomer").hasAuthority("ADMIN")
                        .requestMatchers("/admin/searchExpert").hasAuthority("ADMIN")
                        .requestMatchers("/admin/searchOrders").hasAuthority("ADMIN")


                        .requestMatchers("/customer/addCustomer").permitAll()
                        .requestMatchers("/customer/authenticate").permitAll()
                        .requestMatchers("/customer/saveOrders").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/selectExpertSuggestion").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/addComment").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/onlinePayment/{customerEmail}/{orderId}").permitAll()
                        .requestMatchers("/customer/selectStartWork").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/endOfOrder").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/orderPayment").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/changePassword").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllSubDuty").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllExpert").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllOrderSuggestions").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllOrderWaitingForSpecialistToWorkPlace").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllDoneOrders").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllStartedOrders").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/findAllPaidOrders").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/historyOrders").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/showWallet").hasAuthority("CUSTOMER")
                        .requestMatchers("/customer/onlinePaymentPortal/{customerEmail}/{orderId}").permitAll()
                        .requestMatchers("/customer/generateCaptcha").permitAll()
                        .requestMatchers("/customer/timeout").permitAll()
                        .requestMatchers("/customer/successfullyPayment").permitAll()
                        .requestMatchers("/customer/verifyToken").permitAll()


                        .requestMatchers("/expert/addExpert").permitAll()
                        .requestMatchers("/expert/authenticate").permitAll()
                        .requestMatchers("/expert/addExpertSuggestion").hasAuthority("EXPERT")
                        .requestMatchers("/expert/changePassword").hasAuthority("EXPERT")
                        .requestMatchers("/expert/showScore").hasAuthority("EXPERT")
                        .requestMatchers("/expert/findAllSubDuty").hasAuthority("EXPERT")
                        .requestMatchers("/expert/findAllOrders").hasAuthority("EXPERT")
                        .requestMatchers("/expert/historyOrders").hasAuthority("EXPERT")
                        .requestMatchers("/expert/showWallet").hasAuthority("EXPERT")
                        .requestMatchers("/expert/verifyToken").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
