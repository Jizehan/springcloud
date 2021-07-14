package com.jizehan.algorithms;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Signature {
    public static String getSignature(String status) throws UnsupportedEncodingException {
        // This is the working flow

		String oauth_signature_method = "HMAC-SHA1";
		String oauth_consumer_key = "1MPjWwWakud89ZfWQQolnK9cC";
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string; // any relatively random alphanumeric string will work here. I used UUID minus "-" signs
		String oauth_timestamp = (new Long(System.currentTimeMillis()/1000)).toString(); // get current time in milliseconds, then divide by 1000 to get seconds

		// I'm not using a callback value. Otherwise, you'd need to include it in the parameter string like the example above
		// the parameter string must be in alphabetical order
		String parameter_string = "oauth_consumer_key=" + oauth_consumer_key + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + "&oauth_timestamp=" + oauth_timestamp + "&oauth_version=1.0" + "&status=" + status;// + "&status=" + status
		System.out.println("parameter_string=" + parameter_string);
		String signature_base_string = "POST&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fstatuses%2Fupdate.json&" + URLEncoder.encode(parameter_string, "UTF-8");
		System.out.println("signature_base_string=" + signature_base_string);
//		signature_base_string = "POST&https%3A%2F%2Fapi.twitter.com%2F1.1%2Fstatuses%2Fupdate.json&include_entities%3Dtrue%26oauth_consumer_key%3Dxvz1evFS4wEEPTGEFPHBog%26oauth_nonce%3DkYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1318622958%26oauth_token%3D370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb%26oauth_version%3D1.0%26status%3DHello%2520Ladies%2520%252B%2520Gentlemen%252C%2520a%2520signed%2520OAuth%2520request%2521";
		String oauth_signature = "";
		try {
			oauth_signature = computeSignature(signature_base_string, "dM7goc0FQ0X5giexkbKZVTrDAatAKgogDJ3XtAspKXBbzPMGVU&fg0jFPkQKg7LfVxS3b8g2SjZSLErcqkElPBbtngmQiiGu");  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
//			System.out.println("oauth_signature=" + URLEncoder.encode(oauth_signature, "UTF-8"));
			System.out.println("oauth_signature=" + oauth_signature);
			//OAuth signature	hCtSmYh+iHYCEqBWrE7C7hYmtUk=
			//OAuth signature	hCtSmYh+iHYCEqBWrE7C7hYmtUk=
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String authorization_header_string = "OAuth oauth_consumer_key=\"" + oauth_consumer_key + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" +
				oauth_timestamp + "\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" + oauth_signature + "\"";
		System.out.println("authorization_header_string=" + authorization_header_string);

		return authorization_header_string;
    }

	private static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException {

		SecretKey secretKey = null;

		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");

		mac.init(secretKey);

		byte[] text = baseString.getBytes();

		return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String authorization_header_string = getSignature("hello twitter");
        System.out.println("authorization_header_string = " + authorization_header_string);
    }


}