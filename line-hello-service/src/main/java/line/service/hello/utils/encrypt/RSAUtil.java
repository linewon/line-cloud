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
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import line.service.hello.utils.encrypt.RSAKeyUtil.KeyPairGenerator;

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
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		String data = "linewon";
		String data2 = "notline";
		String alg = "SHA1withRSA";
		String transform = "RSA";
		
		// keyPair generation
		Map<String, RSAKey> pair = KeyPairGenerator.generateKeyPair(512);
		RSAPublicKey publicKey = (RSAPublicKey) pair.get("publicKey");
		RSAPrivateKey privateKey = (RSAPrivateKey) pair.get("privateKey");
		
		// sign & verify
		byte[] signature = Signatures.sign(data.getBytes(), privateKey, alg);
		System.out.println(Signatures.verify(data2.getBytes(), signature, publicKey, alg));
		
		// encrypt & decrypt
		byte[] cipher = Encryptions.encrypt(data.getBytes(), publicKey, transform);
		byte[] plain = Encryptions.decrypt(cipher, privateKey, transform);
		System.out.println(new String(plain));
		
	}
}
