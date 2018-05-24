package line.service.hello.utils.encrypt;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import line.service.hello.utils.HexUtil;

public class RSAKeyUtil {

	private static final String ALG = "RSA";

	public static class KeyPairGenerator {

		/**
		 * 支持的keySize
		 */
		private static final int[] SUPT_KEY_SIZE = { 512, 1024, 2048 };
		/**
		 * Map中存放的key值
		 */
		public static final String PUBLIC_KEY = "publicKey";
		public static final String PRIVATE_KEY = "privateKey";

		/**
		 * 检查输入keySize是否支持
		 */
		private static boolean isKeySizeSupported(int keySize) {
			for (int size : SUPT_KEY_SIZE) {
				if (keySize == size)
					return true;
			}
			return false;
		}

		/**
		 * 生成密钥对 可以根据生成的每月对再进一步转换
		 * 
		 * @throws NoSuchAlgorithmException
		 */
		public static Map<String, RSAKey> generateKeyPair(int keySize) throws NoSuchAlgorithmException {
			if (!isKeySizeSupported(keySize)) {
				throw new IllegalArgumentException("the keySize is not supported!");
			}
			Map<String, RSAKey> keyPairMap = new HashMap<String, RSAKey>();
			java.security.KeyPairGenerator keyPairGen = java.security.KeyPairGenerator.getInstance(ALG);
			keyPairGen.initialize(keySize);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyPairMap.put(PUBLIC_KEY, publicKey);
			keyPairMap.put(PRIVATE_KEY, privateKey);
			return keyPairMap;
		}
	}

	/**
	 * RSA公钥生成与转换 支持X.509, PEM格式的公钥
	 * 
	 * @author line
	 */
	public static class PublicKeyGenerator {

		/**
		 * 根据模和指数生成公钥
		 * 
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 */
		public static RSAPublicKey generatePublicKey(BigInteger modulus, BigInteger exponent)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory kf = KeyFactory.getInstance(ALG);
			return (RSAPublicKey) kf.generatePublic(spec);
		}

		/**
		 * 生成X.509格式的公钥
		 */
		public static String generatePublicKeyInX509(RSAPublicKey publicKey) {
			return HexUtil.toHexString(publicKey.getEncoded());
		}

		public static String generatePublicKeyInPEM(RSAPublicKey publicKey) {
			byte[] bs = HexUtil.toByteArray(generatePublicKeyInX509(publicKey));
			return Base64.getEncoder().encodeToString(bs);
		}

		// /**
		// * DER
		// */
		// public static String generatePublicKeyInDER(RSAPublicKey publicKey) {
		// BigInteger modul = publicKey.getModulus();
		// BigInteger expo = publicKey.getPublicExponent();
		//
		// new RSAPublicKeyStructure(modul, expo).get;
		// }

		/**
		 * 根据X.509格式公钥字符串生成公钥
		 * 
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 * @throws IlleagalArgumentException
		 *             传入字符串中包含非16进制字符
		 */
		public static RSAPublicKey generatePublicKeyFromX509(String publicKeyX509)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(HexUtil.toByteArray(publicKeyX509));
			KeyFactory kf = KeyFactory.getInstance(ALG);
			return (RSAPublicKey) kf.generatePublic(spec);
		}

		public static RSAPublicKey generatePublicKeyFromPEM(String publicKeyPEM)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			byte[] pub = Base64.getDecoder().decode(publicKeyPEM);
			String pubX509 = HexUtil.toHexString(pub);

			return generatePublicKeyFromX509(pubX509);
		}
	}

	/**
	 * RSA私钥生成与转换 支持PKCS#8格式的私钥
	 * 
	 * @author line
	 */
	public static class PrivateKeyGenerator {

		/**
		 * 根据模和指数生成私钥
		 * 
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 */
		public static RSAPrivateKey generatePrivateKey(BigInteger modulus, BigInteger exponent)
				throws NoSuchAlgorithmException, InvalidKeySpecException {

			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, exponent);
			KeyFactory kf = KeyFactory.getInstance(ALG);
			return (RSAPrivateKey) kf.generatePrivate(spec);
		}

		/**
		 * 根据PKCS#8格式私钥字符串生成私钥
		 * 
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 */
		public static RSAPrivateKey generatePrivateKey(String privateKey)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(HexUtil.toByteArray(privateKey));
			KeyFactory keyFactory = KeyFactory.getInstance(ALG);
			RSAPrivateKey priKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
			return priKey;
		}

		/**
		 * 生成PKCS#8格式16进制私钥
		 */
		public static String generatePrivateKey(RSAPrivateKey priKey) {
			return HexUtil.toHexString(priKey.getEncoded());
		}
	}

	public static void main(String[] args) throws Exception {
		 Map<String, RSAKey> keyPair = KeyPairGenerator.generateKeyPair(1024);
		 RSAPublicKey publicKey = (RSAPublicKey) keyPair.get("publicKey");
		 RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.get("privateKey");
		 String pub = PublicKeyGenerator.generatePublicKeyInX509(publicKey);
		 String pri = PrivateKeyGenerator.generatePrivateKey(privateKey);
		
		 System.out.println(pub);
		 System.out.println(pri);

//		String pub = "";
		RSAPublicKey pubK = PublicKeyGenerator.generatePublicKeyFromX509(pub);

		String pem = PublicKeyGenerator.generatePublicKeyInPEM(pubK);
		System.out.println(pem);
	}
}