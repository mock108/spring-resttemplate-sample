package com.example.util;

import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

public class HttpRequestUtil {

	public static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

	@Autowired
	RestTemplate restTemplate;

	/**
	 * REST実行
	 * @param uri
	 * @param httpMethod
	 * @param requestEntity
	 * @return
	 * @throws HttpStatusCodeException
	 */
	public ResponseEntity<String> execRestTemplate(UriComponents uri, HttpMethod httpMethod, HttpEntity<?> requestEntity)
			throws HttpStatusCodeException {
		logging("Request", "URL", uri.toUriString());
		logging("Request", "httpMethod", httpMethod.name());
		logging("Request", requestEntity);

		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(uri.toUri(), httpMethod, requestEntity, String.class);
			logging("Response", "HTTPｽﾃｰﾀｽ", response.getStatusCode().toString());
			logging("Response", response);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			logging("Response", "HTTPｽﾃｰﾀｽ", e.getStatusCode().toString());
			logging("Response", new HttpEntity<String>(e.getResponseBodyAsString(), e.getResponseHeaders()));
			throw e;
		}
		return response;
	}

	/**
	 * 定型ログ
	 * @param string
	 * @return
	 */
	private static void logging(String head, String key, String val) {
		String msg = new StringBuilder()
				.append(String.format(" %-10s", "[" + head + "]"))
				.append(String.format(" %-10s", key)).append(" : ")
				.append(val).toString();
		logger.debug(msg);
	}

	/**
	 * 定型ログ
	 * @param <T>
	 * @param string
	 * @return
	 */
	private static <T extends HttpEntity<?>> void logging(String head, T httpEntity) {
		for (Entry<String, List<String>> httpHeader : httpEntity.getHeaders().entrySet()) {
			String msg = new StringBuilder()
					.append(String.format(" %-10s", "[" + head + "]"))
					.append(String.format(" %-10s", "HTTPﾍｯﾀﾞｰ")).append(" :")
					.append(String.format(" %-25s", httpHeader.getKey())).append(" : ")
					.append(httpHeader.getValue().toString()).toString();
			logger.debug(msg);
		}
		if (httpEntity.getBody() != null) {
			logging(head, "HTTPﾎﾞﾃﾞｨ", httpEntity.getBody().toString());
		}
	}
}
