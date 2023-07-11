package com.abhishek.sampleresourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class ServerConfig {

    private final String introspectionUri;
    private final String clientId;
    private final String clientSecret;
    private final String authServerUrl;
    private final List<String> allowedOrigins;


    public ServerConfig(@Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}") String introspectionUri, @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}") String clientId, @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}") String clientSecret, @Value("${app.auth-server-url}") String authServerUrl, @Value("${app.allowed-origins}") List<String> allowedOrigins) {
        this.introspectionUri = introspectionUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authServerUrl = authServerUrl;
        this.allowedOrigins = allowedOrigins;
    }

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/enduser/register/**").permitAll()
                        .pathMatchers("/messages/**").hasAuthority("SCOPE_api.read")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaqueToken -> opaqueToken
                                .introspector(introspector())
                        )
                );
        return http.cors(Customizer.withDefaults()).csrf(ServerHttpSecurity.CsrfSpec::disable).build();
    }

    @Bean
    public ReactiveOpaqueTokenIntrospector introspector() {
        return new NimbusReactiveOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(this.authServerUrl);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(this.allowedOrigins);
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
