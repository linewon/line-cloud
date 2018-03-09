package line.service.hello.utils;

/**
 * 16进制转换工具类
 * 
 * @author line
 */
public class HexUtil {
	
	/**
	 * 字节数组转16进制字符串
	 */
	public static String toHexString(byte[] data) {
		if (data == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			byte lo = (byte) (0xF & data[i]);
			byte hi = (byte) ((0xF0 & data[i]) >>> 4);
			sb.append("0123456789ABCDEF".charAt(hi)).append("0123456789ABCDEF".charAt(lo));
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转字节数组
	 * @IllegalArgumentException 传入字符串包含非16进制字符时抛出异常
	 */
	public static byte[] toByteArray(String hexStr) throws IllegalArgumentException {
		if (hexStr == null)
			return null;
		if (hexStr.length() % 2 != 0) {
			return null;
		}
		byte[] data = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			char hc = hexStr.charAt(2 * i);
			char lc = hexStr.charAt(2 * i + 1);
			try {
				
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("the input array contains char which is not hex-char!");
			}
			byte hb = toByte(hc);
			byte lb = toByte(lc);
			if ((hb < 0) || (lb < 0)) {
				return null;
			}
			int n = hb << 4;
			data[i] = (byte) (n + lb);
		}
		return data;
	}

	/**
	 * 16进制char转byte
	 * @IllegalArgumentException 传入非16进制字符抛出异常
	 */
	public static byte toByte(char hexChar) throws IllegalArgumentException {
		if ((hexChar >= '0') && (hexChar <= '9'))
			return (byte) (hexChar - '0');
		if ((hexChar >= 'a') && (hexChar <= 'f'))
			return (byte) (hexChar - 'a' + 10);
		if ((hexChar >= 'A') && (hexChar <= 'F'))
			return (byte) (hexChar - 'A' + 10);
		throw new IllegalArgumentException("the input is not hex-char!");
	}
}