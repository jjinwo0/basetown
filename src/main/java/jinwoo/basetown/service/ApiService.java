package jinwoo.basetown.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {

    @Value("${data-api-key}")
    private String key;

    private final String urlStr = "https://openapi.gg.go.kr/PublicTrainingFacilityBasebal?" +
            "KEY="+key+"&" +
            "Type=json&" +
            "pindex=1&" +
            "pSize=100";

    public ResponseEntity<?> fetch() throws UnsupportedEncodingException{
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(urlStr, HttpMethod.GET, entity, Map.class);

        return resultMap;
    }
}
