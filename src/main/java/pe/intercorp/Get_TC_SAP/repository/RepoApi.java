package pe.intercorp.Get_TC_SAP.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pe.intercorp.Get_TC_SAP.entity.TipoCambio;

@Repository
public class RepoApi {
    @Value("${api.url}")
    public String api_url;

    @Value("${api.username}")
    public String api_username;

    @Value("${api.password}")
    public String api_password;

    // private final Logger log = LoggerFactory.getLogger(RepoApi.class);

    public TipoCambio getTipoCambio() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        String stringBody = String.format(
                "{\n    \"E_TIPO_CAMBIO\": [\n        {\n            \"MONEDA_TC\": \"\",\n            \"U_TPCAMBIO\": \"\",\n            \"GDATU\": \"\"\n        }\n    ]\n}");

        RequestBody body = RequestBody.create(stringBody, mediaType);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url("https://"+ api_url +"/zbapi_tipo_cambio_2/ZBAPI_TIPO_CAMBIO_2")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("apikey", api_password)
                .build();
        Response response = client.newCall(request).execute();

        String response_string = response.body().string();

        TipoCambio tipoCambio = objectMapper.readValue(response_string, TipoCambio.class);
          
        return tipoCambio;

    }

}
