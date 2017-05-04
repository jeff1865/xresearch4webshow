package com.yg.webshow.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

public class FileDownloader {

	public void downloadFile(String url, String localFilePath) throws Exception {
		
		URI uri = new URI(url);
		HttpGet httpget = new HttpGet(uri);

		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = httpclient.execute(httpget);
		// check response headers.
		String reasonPhrase = response.getStatusLine().getReasonPhrase();
		int statusCode = response.getStatusLine().getStatusCode();

		System.out.println(String.format("statusCode: %d", statusCode));
		System.out.println(String.format("reasonPhrase: %s", reasonPhrase));

		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		FileOutputStream fos = new FileOutputStream(
				new File(localFilePath));
		byte[] buf = new byte[1024];

		int cnt = -1;
		while ((cnt = is.read(buf)) > -1) {
			fos.write(buf, 0, cnt);
		}
		
		fos.flush();
		fos.close();
		
		is.close();
	}

	public static void main(String... v) throws Exception {
		String url = "http://magicmonster.com";
		url = "http://cache.clien.net/cs2/data/file/park/thumb/728x0_70/20170429135321_UPXu5Y74_LRM_EXPORT_20170429_101430_ann.jpg";
		URI uri = new URI(url);
		HttpGet httpget = new HttpGet(uri);

		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = httpclient.execute(httpget);
		// check response headers.
		String reasonPhrase = response.getStatusLine().getReasonPhrase();
		int statusCode = response.getStatusLine().getStatusCode();

		System.out.println(String.format("statusCode: %d", statusCode));
		System.out.println(String.format("reasonPhrase: %s", reasonPhrase));

		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();

		FileOutputStream fos = new FileOutputStream(
				new File("/Users/1002000/dev/temp20/" + System.currentTimeMillis() + ".jpg"));
		byte[] buf = new byte[1024];

		int cnt = -1;
		while ((cnt = is.read(buf)) > -1) {
			fos.write(buf, 0, cnt);
		}

		fos.flush();
		fos.close();
		is.close();

		// ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 1024);
		//
		// // apache IO util
		// try {
		// System.out.println("start download");
		// IOUtils.copy(content, baos);
		// } finally {
		// // close http network connection
		// content.close();
		// }
		// System.out.println("end download");
		// byte[] bytes = baos.toByteArray();
		// System.out.println(String.format("got %d bytes", bytes.length));
		// System.out.println("HTML as string:" + new String(bytes));
		;
	}
}
