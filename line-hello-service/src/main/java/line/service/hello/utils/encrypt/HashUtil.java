package line.service.hello.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.buf.HexUtils;
import org.bouncycastle.crypto.Digest;

import line.service.hello.utils.HexUtil;

/**
 * hash函数以及hmac
 * 
 * @author line
 */
public class HashUtil {
	
	private static boolean isAlgSupported(String alg, String[] suptAlg) {
		for (String supt : suptAlg) {
			if (supt.equals(alg)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * SHA-256\384\512
	 * 
	 * @author line
	 */
	public static class SHA {

		private static final String[] SUPT_ALG = { "SHA-256", "SHA-384", "SHA-512" };

		public static byte[] digest(byte[] data, String alg) throws NoSuchAlgorithmException {

			if (!isAlgSupported(alg, SUPT_ALG)) {
				throw new NoSuchAlgorithmException("chosen algorithm is not supoorted!");
			}

			MessageDigest messageDigest = MessageDigest.getInstance(alg);
			messageDigest.update(data);
			byte[] digest = messageDigest.digest();
			return digest;
		}

		public static String digest(String data, String alg)
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
			byte[] digest = digest(data.getBytes("UTF-8"), alg);
			return HexUtil.toHexString(digest);
		}
	}

	/**
	 * HMAC with SHA
	 * 
	 * @author line
	 */
	public static class HMAC {

		// 还需要弄一个public的算法名
		private static final String[] SUPT_ALG = { "HmacSHA256" };

		public static byte[] digest(byte[] data, byte[] key, String alg)
				throws NoSuchAlgorithmException, InvalidKeyException {

			if (!isAlgSupported(alg, SUPT_ALG)) {
				throw new NoSuchAlgorithmException("chosen algorithm is not supoorted!");
			}
			SecretKeySpec signingKey = new SecretKeySpec(key, alg);
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			return mac.doFinal(data);
		}
		
		/**
		 * hmac得到的byte[]转化为16进制字符串输出
		 * 
		 * @throws InvalidKeyException
		 * @throws NoSuchAlgorithmException
		 * @throws UnsupportedEncodingException
		 */
		public static String digest(String data, String key, String alg)
				throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
			byte[] result = digest(data.getBytes("UTF-8"), key.getBytes("UTF-8"), alg);
			return HexUtil.toHexString(result);
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		String data = "linewon";
		String alg = "SHA-256";

		byte[] digest = SHA.digest(data.getBytes(), alg);
		System.out.println(HexUtil.toHexString(digest));
		digest = SHA.digest(data.getBytes(), alg);
		System.out.println(HexUtil.toHexString(digest));

		System.out.println(SHA.digest(data, alg));

		String result = HMAC.digest(data, data, "HmacSHA256");
		System.out.println(result);
		result = HMAC.digest(data, data, "HmacSHA256");
		System.out.println(result);
		result = HMAC.digest(data, alg, "HmacSHA256");
		System.out.println(result);
	}
}
