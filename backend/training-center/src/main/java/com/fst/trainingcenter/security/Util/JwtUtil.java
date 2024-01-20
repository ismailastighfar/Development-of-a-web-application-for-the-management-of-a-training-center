package com.fst.trainingcenter.security.Util;

public class JwtUtil {
    public static final String SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    public static final String AUTH_HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final long EXPIRE_ACCESS_TOKEN = 10*60*1000;
    public static final long EXPIRE_REFRESH_TOKEN = 30*60*1000;
    public static final String REFRESH_PATH = "/refreshToken";

    public static class SecurityExceptionMessage {
        public static final String INVALID_CREDENTIALS = "businessException.InvalidCredentials.message";
        public static final String INVALID_TOKEN = "businessException.InvalidRefreshToken.message";
    }
}
