package net.Aituba.HtmlUtils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class HtmlUtils {
    private PoolingHttpClientConnectionManager phccm;

    //创建连接池
    public HtmlUtils() {
        this.phccm = new PoolingHttpClientConnectionManager();
        this.phccm.setMaxTotal(100);
        this.phccm.setDefaultMaxPerRoute(10);
    }

    /**
     * 获取html源码
     *
     * @param url 套图url
     * @return 返回html
     */
    public String getHtml(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getCode() == 200) {
                if (response.getEntity() != null) {
                    String html = EntityUtils.toString(response.getEntity(), "utf-8");
                    return html;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
