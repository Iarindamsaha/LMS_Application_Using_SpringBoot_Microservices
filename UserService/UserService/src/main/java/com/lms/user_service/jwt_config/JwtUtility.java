package com.lms.user_service.jwt_config;

import com.lms.user_service.dto.LoginDto;
import com.lms.user_service.dto.OtpCheckingDTO;
import com.lms.user_service.exceptions.UserException;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    @Autowired
    LoginDto loginDto;

    @Autowired
    OtpCheckingDTO otpDto;

    public String generateToken(LoginDto loginDto){

        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME_IN_MS);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email",loginDto.getEmail());
        claims.put("password",loginDto.getPassword());

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256,SecurityConstants.JWT_SECRET_KEY)
                .compact();

    }

    public boolean validateToken (String token){
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException | MalformedJwtException e){
            throw new UserException("Token Is Invalid Or Expired ", HttpStatus.BAD_GATEWAY);
        }
    }

    public boolean validateTokenForVerify (String token){
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException | MalformedJwtException e){
            return false;
        }
    }

    public LoginDto decodeToken (String token){

        if(validateToken(token)){
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            loginDto.setEmail(claims.get("email").toString());
            loginDto.setPassword(claims.get("password").toString());
            return loginDto;
        }
        else {
            return null;
        }
    }


    public String generateTokenUsingOtp(OtpCheckingDTO otpDto){

        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME_IN_MS);

        Map<String,Object> claims = new HashMap<>();
        claims.put("email",otpDto.getEmail());
        claims.put("otp",otpDto.getOtp());
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256,SecurityConstants.JWT_SECRET_KEY)
                .compact();
    }



    public OtpCheckingDTO decodeTokenInOtp (String token){

        if(validateToken(token)){
            Claims claims = Jwts

                    .parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            otpDto.setEmail(claims.get("email").toString());
            otpDto.setOtp((int) claims.get("otp"));
            return otpDto;
        }
        else {
            return null;
        }
    }


}
