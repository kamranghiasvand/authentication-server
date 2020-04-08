//package com.bluebox.planner.auth.security;
//
//import com.bluebox.planner.auth.persistence.entity.regular.RegularUserEntity;
//import io.jsonwebtoken.Jwts;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Date;
//import java.util.ArrayList;
//
///**
// * @author by kamran ghiasvand
// */
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private AuthenticationManager authenticationManager;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req,
//                                                HttpServletResponse res) throws AuthenticationException {
//        try {
//            RegularUserEntity creds = new ObjectMapper()
//                    .readValue(req.getInputStream(), RegularUserEntity.class);
//
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getEmail(),
//                            creds.getPassword(),
//                            new ArrayList<>())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//JwtTokenUtil.generateToken((User)auth.getPrincipal());
//res.addHeader();
//
////        String token = Jwts.builder()
////                .withSubject(((User) auth.getPrincipal()).getUsername())
////                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
////                .sign(HMAC512(SECRET.getBytes()));
////        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
//    }
//}
