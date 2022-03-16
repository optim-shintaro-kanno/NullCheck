package jp.co.optim.NullCheck;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class KomtraxAPI {
    static final String baseUrl = "https://sandbox.api.komtrax.komatsu/v1/manufactures/components/search";
    private String webAppKey;
    private String appCode;
    private int count;

    public KomtraxAPI(String w, String a, int c){
        this.webAppKey = w;
        this.appCode = a;
        this.count = c;
    }

    public String ExecuteKomtraxAPI() throws URISyntaxException {
        URIBuilder builder = new URIBuilder(baseUrl);

        String requestUrl = builder.toString();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(requestUrl);
        post.addHeader("X-WebApiKey", webAppKey);
        post.addHeader("X-AppCode", appCode);
        post.addHeader("X-Count", String.valueOf(count));
        post.addHeader("X-Fields", "components.component_equipment");

        HttpResponse res = null;
        String result = null;
        try {
            res = httpClient.execute(post);
            result = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONArray Components(String s) throws JSONException {
        JSONObject ob = new JSONObject(s);
        JSONObject res = ob.getJSONObject("result_data");
        JSONArray components = res.getJSONArray("components");
        return components;
    }
}
