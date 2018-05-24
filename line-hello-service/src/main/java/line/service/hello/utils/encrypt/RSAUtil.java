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

import line.service.hello.utils.HexUtil;
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
		
		String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClpBh2i+3CpcJM9ImXXw7XzOQrHN4mRAb2z2LWEuLa103Wi2NXgAKycKl66KLEkb6krYyoMJiBjy3Ry6A5hRAbOA8LGsI0r3GjftH6Lc9eWRDPzX+OPzkP6hSehycS1ppwXuFqctOCqWwPdgA9KtcIcsec87EGLrSUzRohVMi7/QIDAQAB";
		String pri = "30820279020100300D06092A864886F70D0101010500048202633082025F02010002818100A5A418768BEDC2A5C24CF489975F0ED7CCE42B1CDE264406F6CF62D612E2DAD74DD68B63578002B270A97AE8A2C491BEA4AD8CA83098818F2DD1CBA03985101B380F0B1AC234AF71A37ED1FA2DCF5E5910CFCD7F8E3F390FEA149E872712D69A705EE16A72D382A96C0F76003D2AD70872C79CF3B1062EB494CD1A2154C8BBFD0203010001028181009346CF55805F3BE5A45846499C64B1CEE0E7B309ACE03B3631F6FD7B3839AAB92B056AD43E759C704A49F6F5A7DBBD76D3F7578C280141992BF550E73A9D7C4E38D6CF86C4A7922DDFA34527BDE57BC166E68BD6FD6058430406F4040289A2EF6DDAFFD2AF790BA248C66DB71A5BC86452DFFAD1BEA579CB60111C585F3B98E5024100D61F96AA042A162C4072CAB2B68EE63F355F65F08392CA6EA4A1A769E7AAE7CC402484EC0ED7CE98110AB94A13BE0CBE8CD9D27662BC1C44AC21802B2567A2BB024100C60929A23B505B10992520C9525E8F6624B38FA63B9872E2AD4D37D964295EE2D9D0A026C1BC97F9CE4B6F928058A28E35379A842722B5F7E4ECD4B04FE17CA7024100C4BD1179F5899605E2D1965CCE504E4E158429EDEB769C793CDB2B2FABA4E4864F093EF8864AD38FF5589D16EB3CDC08D8F1EAE6CBA17B77BD90AA6AFA5B56B1024100B88F0F7AFF163B5833B17365CC5B0DFF5E92ED7C39AC30DD6A2F3DAE5EF76353BD34FE1C408FD77EDAB058F1CAA55011822D28D6A27669E9F63275613F80E09F0241008952CD7EADB51249115152B92AE606D3A89B192D00E52EBCDEE131E00A7A8A509CCEE49F8B13F0CE3AFD9861DBA5B2362D5EB72F2FC4223D944E24F34136BDC8";
		
		// keyPair generation
//		Map<String, RSAKey> pair = KeyPairGenerator.generateKeyPair(512);
//		RSAPublicKey publicKey = (RSAPublicKey) pair.get("publicKey");
//		RSAPrivateKey privateKey = (RSAPrivateKey) pair.get("privateKey");
		
		RSAPublicKey publicKey = PublicKeyGenerator.generatePublicKeyFromPEM(pub);
		RSAPrivateKey privateKey = PrivateKeyGenerator.generatePrivateKey(pri);
		
		// sign & verify
//		byte[] signature = Signatures.sign(data.getBytes(), privateKey, alg);
//		System.out.println(Signatures.verify(data.getBytes(), signature, publicKey, alg));
		
		// encrypt & decrypt
//		byte[] cipher = Encryptions.encrypt(data.getBytes(), publicKey, transform);
		
		String cipherString = "6dda51e4e3e0bea9c2f349f7a09f41da6d4757e365ebe39e4eeb086e3236615bb9076791cc41775a99082c2d7373880accad0a02bdd274b78aab10f5e77e056c1238b753a8e7f263e8072f2bb569e747bbe77b048afadd8cb37e4f7ba083664f207462023d49487878978f71598eb496a796afa7c48b8e7f76ae20e79142626f";
		byte[] cipher = HexUtil.toByteArray(cipherString);
		byte[] plain = Encryptions.decrypt(cipher, privateKey, transform);
		System.out.println(new String(plain));
		
	}
}
