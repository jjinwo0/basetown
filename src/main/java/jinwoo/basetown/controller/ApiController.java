package jinwoo.basetown.controller;

import jinwoo.basetown.form.ChoiceStadiumForm;
import jinwoo.basetown.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/api")
    public String stadiumForm(Model model) throws Exception{
        model.addAttribute("choiceStadiumForm", new ChoiceStadiumForm());
        ArrayList<HashMap<String, String>> map = apiService.RequestStadium();
        System.out.println(map.get(1));
        return "/members/choiceStadiumForm";
    }

    @PostMapping("/api")
    public String stadium(@Valid ChoiceStadiumForm form) throws Exception{
        ArrayList<HashMap<String, String>> map = apiService.RequestStadium();
        System.out.println(map.get(1));

        return "redirect:/";
    }
}
