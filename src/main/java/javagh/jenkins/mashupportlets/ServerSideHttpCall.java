package javagh.jenkins.mashupportlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Used to proxy requests. Used in portlet beans methods with annotation @JavaScriptMethod.
 * 
 * @author henzlerg
 * 
 */
public class ServerSideHttpCall implements HttpResponse {

    private static final String ERROR = "ERROR: ";

    private static final String HEADER_ORIG_RESPONSE_MESSAGE = "X-OriginalResponseMessage";
    private static final String HEADER_ORIG_RESPONSE_CODE = "X-OriginalResponseCode";

    private final String urlStr;
    private final String httpBasicAuthUser;
    private final String httpBasicAuthPw;
    
    
    public ServerSideHttpCall(String urlStr, String httpBasicAuthUser, String httpBasicAuthPw) {
        this.urlStr = urlStr;
        this.httpBasicAuthUser = httpBasicAuthUser;
        this.httpBasicAuthPw = httpBasicAuthPw;
    }

    public void generateResponse(StaplerRequest request, StaplerResponse response, Object node) throws IOException, ServletException {

        HttpURLConnection urlCon = null;

        InputStream inputStream = null;
        String out = "";
        try {
            URL url = new URL(urlStr);
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(30000); 
            urlCon.setReadTimeout(30000);

            if(StringUtils.isNotBlank(httpBasicAuthUser)) {
            	final String strEncoding = "UTF-8";
                String encoded = new String(Base64.encodeBase64((httpBasicAuthUser+":"+httpBasicAuthPw).getBytes(strEncoding)), strEncoding); 
                urlCon.setRequestProperty("Authorization", "Basic "+encoded); 
            } 
            
            response.setHeader(HEADER_ORIG_RESPONSE_CODE, String.valueOf(urlCon.getResponseCode()));
            response.setHeader(HEADER_ORIG_RESPONSE_MESSAGE, urlCon.getResponseMessage());
            response.setContentType(urlCon.getContentType());
            response.setCharacterEncoding(urlCon.getContentEncoding());

            inputStream = urlCon.getInputStream();
            out = IOUtils.toString(inputStream);

        } catch (Exception e) {

            if (urlCon != null && (inputStream = urlCon.getErrorStream()) != null) {
                try {
                    out = ERROR + IOUtils.toString(inputStream);
                } catch (Exception ex) {
                    out = ERROR + "Sending error stream failed: " + ex + " Cause: " + e;
                }
            } else {
                out = ERROR + e;
            }

        } finally {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.print(out);
            IOUtils.closeQuietly(responseWriter);
            IOUtils.closeQuietly(inputStream);
        }

    }
}
