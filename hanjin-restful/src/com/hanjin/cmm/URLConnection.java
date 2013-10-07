package com.hanjin.cmm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

public class URLConnection {

	/**
	 * 호출 url의 내용 및 cookie 가져오기.
	 * @param url 경로
	 * @param data 데이터
	 * @param cookie 쿠키
	 * @return
	 */
	public static Map<String, String> getURLConnection(String url, String data, String cookie) throws Exception {
		Map<String, String> content = new HashMap<String, String>();

		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {
			public boolean isRedirected(HttpRequest request,
					HttpResponse response, HttpContext context) throws ProtocolException {
				boolean isRedirect = false;
					isRedirect = super.isRedirected(request, response, context);
				if (!isRedirect) {
					int responseCode = response.getStatusLine()
							.getStatusCode();
					if (responseCode == 301 || responseCode == 302) {
						return true;
					}
				}
				return isRedirect;
			}
		});
		HttpPost postRequest = new HttpPost(url);

		StringEntity input = new StringEntity(data);
		input.setContentType("application/x-www-form-urlencoded");
		postRequest.setEntity(input);
		
		postRequest.addHeader("Cookie", cookie);
		
		HttpResponse response = httpClient.execute(postRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent()), "UTF-8"));

		String responseText = "";
		String output;
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			responseText += output;
		}
		
		content.put("responseText", responseText);

		// get cookieStore
		CookieStore cookieStore = httpClient.getCookieStore();
		// get Cookies
		List<Cookie> cookies = cookieStore.getCookies();
		for ( int i = 0; i < cookies.size(); i++ ) {
			content.put(cookies.get(i).getName(), cookies.get(i).getValue());
		}

		httpClient.getConnectionManager().shutdown();

		return content;
	}
}
