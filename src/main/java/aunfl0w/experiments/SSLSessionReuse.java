package aunfl0w.experiments;

import static java.lang.System.out;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * Simple SSL Session Reuse test.
 * Show that Apache HTTP Client left to own doesn't reuse ssl sessions
 * See https://tools.ietf.org/html/rfc5077
 */
public class SSLSessionReuse {
	public static void main(String[] args) throws Throwable {

		if (args.length <= 0) {
			usage();
			return;
		}

		String URL = args[0];
		boolean useSession = Boolean.parseBoolean(args[1]);
		
		out.print(String.format("Running with %s %s\n", args[0], args[1]));
		
		SSLContext slc = null;
		if (useSession)
		slc = setupSSLSessions();

		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			readURLDataPrintSize(URL, slc, useSession);
			long end = System.currentTimeMillis();
			out.println("Took MS : " + (end - start));
		}
	}

	/**
	 * create a SSLContext 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static SSLContext setupSSLSessions() throws NoSuchAlgorithmException, KeyManagementException {

		//trust any server for testing
		//don't use this in a real application
		TrustManager[] trustAllCerts = new TrustManager[] { 
			    new X509TrustManager() {
			    	public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
			    		return new X509Certificate[0];
			    	} 
			    	public void checkClientTrusted( 
			    			java.security.cert.X509Certificate[] certs, String authType) {
			    	} 
			    	public void checkServerTrusted( 
			    			java.security.cert.X509Certificate[] certs, String authType) {
			    	}
					
					}
				};

		SSLContext sslcontext = SSLContext.getInstance("SSL");
        out.println("SSLContext SessionTimeout:" + sslcontext.getClientSessionContext().getSessionTimeout());
        out.println("SSLContext SessionTimeout:" + sslcontext.getClientSessionContext().getSessionCacheSize());

        sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
        return sslcontext;
	}

	/**
	 * make a request call and report the size of the output.
	 * @param URL
	 * @param slc
	 * @param useSLC
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	static void readURLDataPrintSize(String URL, SSLContext slc, boolean useSLC) throws IOException, ClientProtocolException {
		HttpClient client = null;
		if(useSLC)
			client = HttpClients.custom().setSSLContext(slc).build();
		else
			client = HttpClients.custom().build();

		HttpGet httpGet = new HttpGet(URL);

		out.println(httpGet.getRequestLine());

		CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpGet, getContext());
		HttpEntity entity = response.getEntity();
		out.println(response.getStatusLine());
		out.println("Response Length: " + EntityUtils.toString(entity).length());
		response.close();
	}

	/**
	 * make a request header that looks somewhat realistic
	 * @return
	 */
	private static HttpContext getContext() {
		HttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(CoreProtocolPNames.USER_AGENT,
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		return ctx;
	}

	private static void usage() {
		out.println("[URL] Use Context [true|false]");

	}
}
