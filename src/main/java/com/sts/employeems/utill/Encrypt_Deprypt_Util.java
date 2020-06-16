package com.sts.employeems.utill;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
public class Encrypt_Deprypt_Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Encrypt_Deprypt_Util.class);

	private static final String ALGO = "AES";
	private static final byte[] keyValue = "SpringSecretKeys".getBytes();

	static IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);

	public String encrypt(String data) throws Exception {

		String encryptedValue = null;

		Key key = null;
		byte[] encVal = null;
		Cipher cipher = null;

		try {

			LOGGER.info("Start of encrypt(...) ");

			Security.addProvider(new BouncyCastleProvider());
			key = generateKey();
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			encVal = cipher.doFinal(data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);

			LOGGER.info("End of encrypt(...) ");
		} catch (Exception e) {
			LOGGER.error("Error while encrypting the data", e);
		}
		return encryptedValue;
	}

	public String decrypt(String encryptedValue) throws Exception {

		String decryptedValue = null;

		Key key = null;
		Cipher cipher = null;
		byte[] decVal = null;
		byte[] decoadedValue = null;

		try {

			LOGGER.info("Start of decrypt(...)");

			key = generateKey();
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			decoadedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
			decVal = cipher.doFinal(decoadedValue);
			decryptedValue = new String(decVal);

			LOGGER.info("Start of decrypt(...)");
		} catch (Exception e) {
			LOGGER.error("Error while decrypting the data", e);
		}
		return decryptedValue;
	}

	public Key generateKey() {
		return new SecretKeySpec(keyValue, ALGO);
	}

}
