package jinwoo.basetown.controller;

import jinwoo.basetown.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/openApi")
    public String fetch() throws UnsupportedEncodingException{
        return apiService.fetch().getBody().toString();
    }
}
