package kfk.http.inf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

/**
 * HTTP�������
 * 
 * @author kfk
 * 
 */
public class HttpRequester {

	private String defaultContentEncoding;
	public String returnJsonString;

	public void HttpReauester() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}

	/**
	 * ����Get����
	 * 
	 * @param urlString
	 *            URL��ַ
	 * 
	 * @return HttpResponse����
	 * @throws IOException
	 */
	public HttpResponse sendGet(String urlString) throws IOException {
		return this.send(urlString, "GET", null, null);
	}

	/**
	 * ����GET����
	 * 
	 * @param urlString
	 *            URL��ַ
	 * @param params
	 *            ��������
	 * @return ��Ӧ����
	 * @throws IOException
	 */
	public HttpResponse sendGet(String urlString, Map<String, String> params)
			throws IOException {
		return this.send(urlString, "GET", params, null);
	}

	/**
	 * ����GET����
	 * 
	 * @param urlString
	 *            URL��ַ
	 * @param params
	 *            ��������
	 * @param propertys
	 *            ��������
	 * @return ��Ӧ����
	 * @throws IOException
	 */
	public HttpResponse sendGet(String urlString, Map<String, String> params,
			Map<String, String> propertys) throws IOException {
		return this.send(urlString, "GET", params, propertys);
	}

	/**
	 * ����POST����
	 * 
	 * @param urlString
	 *            URL��ַ
	 * @return ��Ӧ����
	 * @throws IOException
	 */
	public HttpResponse sendPost(String urlString) throws IOException {
		return this.send(urlString, "POST", null, null);
	}

	/**
	 * ����POST����
	 * 
	 * @param urlString
	 *            URL��ַ
	 * @param params
	 *            ��������
	 * @return ��Ӧ����
	 * @throws IOException
	 */
	public HttpResponse sendPost(String urlString, Map<String, String> params)
			throws IOException {
		return this.send(urlString, "POST", params, null);
	}

	/**
	 * ����POST����
	 * 
	 * @param urlString
	 *            URL��ַ
	 * @param params
	 *            ��������
	 * @param propertys
	 *            ��������
	 * @return ��Ӧ����
	 * @throws IOException
	 */
	public HttpResponse sendPost(String urlString, Map<String, String> params,
			Map<String, String> propertys) throws IOException {
		return this.send(urlString, "POST", params, propertys);
	}

	private HttpResponse send(String urlString, String method,
			Map<String, String> params, Map<String, String> properties)
			throws IOException {
		HttpURLConnection urlConnection = null;
		/**
		 * GET method
		 */
		if (method.equalsIgnoreCase("GET") && params != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : params.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(params.get(key));
				i++;
			}
			urlString += param;
		}
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();

		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);

		if (properties != null)
			for (String key : properties.keySet()) {
				urlConnection.addRequestProperty(key, properties.get(key));
			}
		/**
		 * POST method
		 */
		if (method.equalsIgnoreCase("POST") && params != null) {
			StringBuffer param = new StringBuffer();
			for (String key : params.keySet()) {
				param.append("&");
				param.append(key).append("=").append(params.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}
		return this.makeContent(urlString, urlConnection);
	}

	/**
	 * �õ���Ӧ����
	 * 
	 * @param urlString
	 * @param urlConnection
	 * @return ��Ӧ����
	 */
	private HttpResponse makeContent(String urlString,
			HttpURLConnection urlConnection) throws IOException {
		HttpResponse httpResponser = new HttpResponse();
		try {
			InputStream in = urlConnection.getInputStream();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			/**
             * 
             */
			// FileOutputStream file = new FileOutputStream("E:\\response.txt");
			// file.write(b);(bufferedReader);
			/**
             * 
             */
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = this.defaultContentEncoding;

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();
			// System.out.println(urlConnection.getResponseMessage());
			// httpResponser.content = new String(temp.toString().getBytes(),
			// ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

	}

	/**
	 * Ĭ�ϵ���Ӧ�ַ���
	 */
	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}

	/**
	 * ����Ĭ�ϵ���Ӧ�ַ���
	 */
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}

}
