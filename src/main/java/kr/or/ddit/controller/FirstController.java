package kr.or.ddit.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String nicetoMeetYou(Model model) {
        model.addAttribute("username","소똥");
        return "greeting";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname","메뚜기");
        return "goodbye";
    }
}
