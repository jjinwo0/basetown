package jinwoo.basetown.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ApiService {

    @Value("${data-api-key}")
    private String key;
    public ArrayList<HashMap<String, String>> stadium = new ArrayList<HashMap<String, String>>();

    // 일별 박스오피스 요청 함수
    public ArrayList<HashMap<String, String>> RequestStadium() throws Exception {

        String AnimalURL = "https://openapi.gg.go.kr/PublicTrainingFacilityBasebal?";
        String apiKey = key;
        String pindex = "1";
        String pSize = "100";
        String type="json";

        // StringBuilder는 String에 String을 더할 때 연산을 조금 더 빠르게 해줄 수 있는 객체임
        StringBuilder urlBuilder = new StringBuilder(AnimalURL); /* 요청 주소 */

        // 기존 주소에 append 함수로 파라미터 추가
        urlBuilder.append("?" + URLEncoder.encode("KEY","UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8")); /* 인증 키 */
        urlBuilder.append("&" + URLEncoder.encode("pindex","UTF-8") + "=" + URLEncoder.encode(pindex, "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
        urlBuilder.append("&" + URLEncoder.encode("pSize","UTF-8") + "=" + URLEncoder.encode(pSize, "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
        // 기존 주소에 모든 파라미터를 합쳤으면 주소 형태의 객체로 바꿔준다.
        URL url = new URL(urlBuilder.toString());

        //System.out.println(urlBuilder.toString());
        // URL 주소로 연결을 활성화 시킨다
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 방식은 GET 방식
        conn.setRequestMethod("GET");

        BufferedReader rd;
        // 만약 서버가 정상적으로 데이터를 받았으면 rd라는 버퍼에 응답한 결과 값을 담는다
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        // 실패했으면 rd라는 버퍼에 응답한 에러 결과 값을 담는다.
        else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        // 버퍼에 담은 값을 String 형태로 만들어준다.
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        // 버퍼 종료
        rd.close();
        // URL 연결 종료
        conn.disconnect();

        // 응답 받은 결과 값을 body 변수에 저장
        String body = sb.toString();

        // 응답 받은 결과를 파싱하기(나누기)위해 JSON Parser객체 생성
        JSONParser parser = new JSONParser();
        try {
            // 파서로 응답 받은 결과 body를 쪼개기
            JSONObject obj = (JSONObject)parser.parse(body);

            long count = (long)obj.get("currentCount");// 카운트 값 가져오기

            String c = Long.toString(count);

            // 그 하위 항목중 dailyBoxOfficeList를 가져오는데 배열이기때문에 JSONArray로 가져옴
            JSONArray data = (JSONArray)obj.get("data");

            // 배열을 돌면서
            for(int i=0;i<data.length();i++){
                HashMap<String, String> list = new HashMap<String, String>();
                JSONObject tmp = (JSONObject)data.get(i);//인덱스 번호로 접근해서 첫번째 부터 가져오기

                // 그 객체 안에 있는 rank 가져오기
                // 그 객체 안에 있는 movieNm 가져오기
                String stadiumName = (String)tmp.get("FACLT_NM");

                String address = (String)tmp.get("SIGUN_NM");

                //System.out.println("구분 : " + Category + ", 치료소명 : "+ Name + ", 주소 : " + Location);

                list.put("stadiumName", stadiumName);
                list.put("address", address);
                stadium.add(list);

            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println(stadium);
        }
        return stadium;
    }
}
