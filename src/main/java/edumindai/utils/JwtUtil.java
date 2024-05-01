package edumindai.utils;


import edumindai.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;


/**
 * JWT工具类
 */

public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 7*24*24*60 * 60 *1000L;// 60 * 60 *1000  一个小时 24*7天
    //设置秘钥明文
    public static final String JWT_KEY = "chaoxin";

    public static String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // 设置User对象的属性到claims中
        claims.put("userDetails", userDetails);

        // 生成JWT
        String token = JwtUtil.createJWT(claims);

        return token;
    }

    public static UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = parseJWT(token);

            UserDetails userDetails = (UserDetails) claims.get("userDetails");

            return userDetails;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }


    public static String createJWT(Map<String, Object> map) {
        JwtBuilder builder = getJwtBuilderMap(map,getUUID());
        return builder.compact();
    }


    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    private static JwtBuilder getJwtBuilderMap(Map<String, Object> map, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setClaims(map)   // 主题  可以是JSON数据
                .setIssuer("sg")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey);//使用HS256对称加密算法签名, 第二个参数为秘钥
    }





}
