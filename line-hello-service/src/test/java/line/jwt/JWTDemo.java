package line.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import line.service.hello.entity.others.Hello;

/**
 * https://github.com/auth0/java-jwt
 * 
 * @author line
 *
 */
public class JWTDemo {

	public static void main(String[] args) throws JsonProcessingException {
		Algorithm alg = null;
		try {
			alg = Algorithm.HMAC256("fuck");
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		test2(alg);
	}

	public static void test1(Algorithm alg) {

		// String token = JWT.create().withIssuer("test").sign(alg);
		String token = JWT.create().withExpiresAt(new Date()).sign(alg);

		System.out.println(token);
	}

	public static void test2(Algorithm alg) throws JsonProcessingException {
		Map<String, Object> claims = new HashMap<>();
		Hello hello = new Hello("linewon", "groupt");
		claims.put("hello", hello);

		ObjectMapper mapper = new ObjectMapper();
		String helloJson = mapper.writeValueAsString(hello);
		
		System.out.println(helloJson);
		// 生成token
		String token = JWT.create()
				.withClaim("hello1", helloJson)
				.withClaim("hello2", helloJson)
				.sign(alg);

		System.out.println(token);
		
		// 解析token
		DecodedJWT jwt = JWT.decode(token);
		Map<String, Claim> claims1 = jwt.getClaims();
		for(Map.Entry<String, Claim> entry : claims1.entrySet()) {
			System.out.println(entry.getKey());
			Claim clm = entry.getValue();
			System.out.println(clm.asString());
		}
		
		// 可以乱序插入，但是不能错，否则抛出invalidClaim异常
		 JWTVerifier verifier = JWT.require(alg)
			        .withClaim("hello2", helloJson)
			        .withClaim("hello1", helloJson)
			        .build();
		 
		 // 验证token
		 DecodedJWT decodedJWT = verifier.verify(token);
		 System.out.println();
		 Claim clm2 = decodedJWT.getClaim("hello2");
		 System.out.println(clm2.asString());
	}
	
	public static void test3() {
		JWTCreator.Builder builder = JWT.create();
		builder.withClaim("1", "1");
	}
}
