package line.service.hello.utils.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import line.service.hello.utils.encrypt.RSAKeyUtil.KeyPairGenerator;
import line.service.hello.utils.encrypt.RSAKeyUtil.PrivateKeyGenerator;
import line.service.hello.utils.encrypt.RSAKeyUtil.PublicKeyGenerator;

/**
 * RSA公钥加密算法 签名、验签、公钥加密、私钥解密 私钥加密就是签名，公钥解密就是验签
 * 
 * @author line
 */
public class RSAUtil {

	/**
	 * 签名sign和验签verify
	 * 
	 * @author line
	 */
	public static class Signatures {

		/**
		 * 签名
		 * 
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeyException
		 * @throws SignatureException
		 */
		public static byte[] sign(byte[] data, PrivateKey privateKey, String algorithm)
				throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
			Signature instance = Signature.getInstance(algorithm);
			instance.initSign(privateKey);
			instance.update(data);
			return instance.sign();
		}

		/**
		 * 验签
		 * 
		 * @throws NoSuchAlgorithmException 
		 * @throws InvalidKeyException 
		 * @throws SignatureException 
		 */
		public static Boolean verify(byte[] data, byte[] signature, PublicKey publicKey, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
			Signature instance = Signature.getInstance(algorithm);
			instance.initVerify(publicKey);
			instance.update(data);
			return instance.verify(signature);
		}
	}

	/**
	 * 加密encrypt和解密decrypt
	 * 
	 * @author line
	 */
	public static class Encryptions {
		/**
		 * 公钥加密
		 * 
		 * @throws NoSuchPaddingException 
		 * @throws NoSuchAlgorithmException 
		 * @throws InvalidKeyException 
		 * @throws BadPaddingException 
		 * @throws IllegalBlockSizeException 
		 */
		public static byte[] encrypt(byte[] data, PublicKey publicKey, String transformation)
				throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		}

		/**
		 * 私钥解密
		 * 
		 * @throws NoSuchPaddingException 
		 * @throws NoSuchAlgorithmException 
		 * @throws InvalidKeyException 
		 * @throws BadPaddingException 
		 * @throws IllegalBlockSizeException 
		 */
		public static byte[] decrypt(byte[] data, PrivateKey privateKey, String transformation)
				throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		}
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

		String data = "linewon";
		String data2 = "notline";
		String alg = "SHA1withRSA";
		String transform = "RSA";
		
		String pub = "30819F300D06092A864886F70D010101050003818D00308189028181009EF5FAC45191233F23123A6248CE051C16D564D76BC22A9BAFB828791BD060C563A17A364B6A6587709C810E38882324F47AD27CF311CDA0706568A2AAFF2ED10C53EB9021C13FBC8FD44DB9639C224A60134B53AB21CDE13566AEA09A4D5B18C595B5AC3771DC283CC917E001AAD6805EA25A8CCC33EE2CF78EAA41282E76AF0203010001";
		String pri = "30820276020100300D06092A864886F70D0101010500048202603082025C020100028181009EF5FAC45191233F23123A6248CE051C16D564D76BC22A9BAFB828791BD060C563A17A364B6A6587709C810E38882324F47AD27CF311CDA0706568A2AAFF2ED10C53EB9021C13FBC8FD44DB9639C224A60134B53AB21CDE13566AEA09A4D5B18C595B5AC3771DC283CC917E001AAD6805EA25A8CCC33EE2CF78EAA41282E76AF0203010001028180242AB52E7B9C791781644152615C367F68968EC610A771C85057A725BEF54F3F39E6E0867FEC2A438194AA2C51227054952768C2B30F26E8CD09D59FC5262041648F3650842FF872B1C80BBD0BAB1A21CBE75B0B9D7585E7C3A8BAC115399DBC961F093F2264FB2F6854863432538E86FDC1AB6BB04747BA16C2582CCDE0C449024100F37727702572EBF53370FBBC59729CFD7B9DC37E1BDFE01AF6D2FD41F377D463873742679272C8E12D3FA32E1FD1B3127E338F9082EF9F5BBE5899575DE194AD024100A72510A1F15815D2C1C165186F065886CD1FDC496779C593F2B7C62599DC1E9C9790E2960329EF67E52EEF2547ADD31F6095A46B379E3C8F900ACD70380C884B024100DF0B624E1380DD3A9E0DFC11E9C05EA2BB05257BFAFD76CA3D25BE6F1525CAA603D2543B9EA719F1710AC3EC15AA7F6B89C2A2DBB998DD5DA75C73DEDE5B336102400D340F594B9052C1004F3FA62B770BDD322CF720D14CBC3FD0A795BE78A5FBC110F400022BA3CF669CA3157F136776CAFEBD336CC8CAF62BBD0C2D6854F5207B024066B51211D3319E095A515FF99BBF5AF4123ACCC64D1515744288732F10D9EBF0B290C4F186A64A1AD10F7C2534F29A5E1E6B6D23983D20DAF98E4B6D75C41676";
		
		// keyPair generation
//		Map<String, RSAKey> pair = KeyPairGenerator.generateKeyPair(512);
//		RSAPublicKey publicKey = (RSAPublicKey) pair.get("publicKey");
//		RSAPrivateKey privateKey = (RSAPrivateKey) pair.get("privateKey");
		
		RSAPublicKey publicKey = PublicKeyGenerator.generatePublicKeyFromX509(pub);
		RSAPrivateKey privateKey = PrivateKeyGenerator.generatePrivateKey(pri);
		
		// sign & verify
		byte[] signature = Signatures.sign(data.getBytes(), privateKey, alg);
		System.out.println(Signatures.verify(data.getBytes(), signature, publicKey, alg));
		
		// encrypt & decrypt
		byte[] cipher = Encryptions.encrypt(data.getBytes(), publicKey, transform);
		byte[] plain = Encryptions.decrypt(cipher, privateKey, transform);
		System.out.println(new String(plain));
		
	}
}
