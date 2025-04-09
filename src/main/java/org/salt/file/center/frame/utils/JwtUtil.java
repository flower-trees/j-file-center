/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.salt.file.center.frame.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final SecretKey key = Keys.hmacShaKeyFor("mySecretKey1234567890mySecretKey1234567890".getBytes());

    private static final String SUBJECT = "UserInfo";
    private static final String USER_JSON = "userJson";
    private static final long EXPIRATION_TIME = 3600_000; // 1 hour

    public static String generateToken(String userJson) {
        return Jwts.builder()
                .subject(SUBJECT)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .claims(Map.of(USER_JSON, userJson))
                .compact();
    }

    public static String parseToken(String token) {
        return (String) Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(USER_JSON);
    }

    public static void main(String[] args) {
        String token = generateToken("{\"user_id\":1}");
        System.out.println(token);
        String userJson = parseToken(token);
        System.out.println(userJson);
    }
}