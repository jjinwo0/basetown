package jinwoo.basetown.controller;

import jinwoo.basetown.entity.Member;
import jinwoo.basetown.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("Basetown : Home Page(none login)");
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            log.info("Basetown : Home Page(none login)");
            return "home";
        }

        model.addAttribute("loginMember", loginMember);
        log.info("Basetown : Home Page(login)");
        return "loginHome";
    }
}
