package com.example.hotelreservation.security;

import com.example.hotelreservation.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for processing JWT tokens in HTTP requests.
 * This filter checks the Authorization header for a JWT, validates it, and sets the authentication context if the token is valid.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Filters incoming requests to check for a valid JWT token.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @param chain    the filter chain.
     * @throws ServletException if an error occurs during request processing.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extract the Authorization header from the request
        final String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT from the Authorization header
            String jwt = authorizationHeader.substring(7);
            // Extract the username from the JWT
            String username = jwtUtil.extractUsername(jwt);

            // Proceed if the username is present and no authentication is currently set
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details based on the extracted username
                UserDetails userDetails = this.userService.loadUserByUsername(username);

                // Validate the JWT token
                if (jwtUtil.validateToken(jwt, userDetails)) {

                    // Create an authentication token for the user and set it in the security context
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    // Log successful authentication
                    System.out.println("User authenticated: " + username);
                } else {
                    // Log invalid token
                    System.out.println("Invalid JWT token");
                }
            } else {
                // Log missing username
                System.out.println("Username is null");
            }
        } else {
            // Log missing Authorization header or invalid format
            System.out.println("Authorization header is missing or does not start with Bearer");
        }

        // Continue with the next filter in the chain
        chain.doFilter(request, response);
    }
}
