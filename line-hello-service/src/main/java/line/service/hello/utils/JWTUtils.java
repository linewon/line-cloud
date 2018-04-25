package line.service.hello.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import line.service.hello.common.StateConstant;

public class JWTUtils {

	@Value("${secret.jwt.hs256}")
	private static String secret;
	private static Algorithm alg = null;

	public static String getToken(String clmName, String clmJson) throws IllegalArgumentException, UnsupportedEncodingException {

		// If a Claim couldn't be converted to JSON
		// or the Key used in the signing process was invalid
		// a JWTCreationException will raise.
		chooseAlgrithm();
		JWTCreator.Builder builder = JWT.create();
		buildCreator(builder, clmName, clmJson);
		String token = builder.sign(alg);
		return token;
	}

	private static void chooseAlgrithm() throws IllegalArgumentException, UnsupportedEncodingException {
		if (alg == null) {
			alg = Algorithm.HMAC256(secret);
		}
	}

	private static void buildCreator(JWTCreator.Builder builder, String clmName, String clmJson) {
		builder.withClaim(clmName, clmJson);
	}

	private static void buildCreator(JWTCreator.Builder builder, Map<String, Object> claims) {
		for (Map.Entry<String, Object> claim : claims.entrySet()) {
			String name = claim.getKey();
			Object value = claim.getValue();
			if (value.getClass() == String.class) {
				builder.withClaim(name, (String) value);
			} else if (value.getClass() == Integer.class) {
				builder.withClaim(name, (Integer) value);
			} else {
				// 这么多类一个个判断？这个垃圾库真不好用。
			}
		}
	}

	public static int authToken(String clmName, String clmJson, String token) throws IllegalArgumentException, UnsupportedEncodingException {

		chooseAlgrithm();
		JWTVerifier verifier = JWT.require(alg).withClaim(clmName, clmJson).build();
		DecodedJWT jwt = null;
		int state = StateConstant.VALID_TOKEN;
		try {
			jwt = verifier.verify(token);
			
		} catch (SignatureVerificationException e) { // 真的是个垃圾库，验签失败和token过期了能用一个异常抛出吗？？？
			state = StateConstant.INVALID_TOKEN;
		}

		return state;
	}
}
