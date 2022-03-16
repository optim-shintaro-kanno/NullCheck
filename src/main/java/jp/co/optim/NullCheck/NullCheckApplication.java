package jp.co.optim.NullCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.*;
import java.io.PrintWriter;
import java.net.URISyntaxException;

@SpringBootApplication
public class NullCheckApplication {

	public static void main(String[] args) throws URISyntaxException, JSONException {
		SpringApplication.run(NullCheckApplication.class, args);

		PrintWriter out = new PrintWriter(System.out);
		Dotenv dotenv = Dotenv.load();

		KomtraxAPI komtraxAPI = new KomtraxAPI(dotenv.get("WEB_API_KEY"), dotenv.get("APP_CODE"), 10000);

		JSONArray components = komtraxAPI.Components(komtraxAPI.ExecuteKomtraxAPI());

		for (int i = 0; i < components.length(); i++){

			JSONObject compEq = components.getJSONObject(i);

			JSONObject cq = compEq.getJSONObject("component_equipment");

			if (!cq.get("logical_name").equals(null)){
				out.println(cq.get("logical_name"));
			}
		}

		out.flush();

	}
}
