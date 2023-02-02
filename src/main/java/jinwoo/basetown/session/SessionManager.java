package jinwoo.basetown.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    //상수화
    public static final String SESSTION_COOKIE_NAME = "mySessionId";

    //스프링 아이디와 객체를 Map으로 저장
    //ConcurrentHashMap<>(): 동시성을 위해 사용하는 맵 유형으로, 멀티스레드 환경에서 사용할 수 있음.
    //검색과 업데이트 시 동시성 성능을 높임
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response){

        //session id 생성 및 세선에 저장
        //randomUUID(): 랜덤 값 생성
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        //쿠키 생성
        Cookie myCookie = new Cookie(SESSTION_COOKIE_NAME, sessionId);
        response.addCookie(myCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSTION_COOKIE_NAME);
        if (sessionCookie == null)
            return null;
        return sessionStore.get(sessionCookie.getValue());
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName){
        if (request.getCookies() == null)
            return null;
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSTION_COOKIE_NAME);
        if (sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }
}
