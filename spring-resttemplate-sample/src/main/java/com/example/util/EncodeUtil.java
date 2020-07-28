package com.example.util;

import java.nio.charset.StandardCharsets;

import org.springframework.web.util.UriUtils;

public class EncodeUtil {

	/**
	 * RFC3986に準拠したURLエンコード
	 */
	public static String urlEncode(String string) {
		return UriUtils.encode(string, StandardCharsets.UTF_8);
	}

	/**
	 * URLデコーダ
	 */
	public static String decode(String value) {
		return UriUtils.decode(value, StandardCharsets.UTF_8);
	}
}
