package lukuvinkit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// this can be turned into lukuvinkkicontroller if needed
@Controller
public class Controllers {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<a href='/other'>linkki</a>";
    }

    @GetMapping("/other")
    @ResponseBody
    public String other() {
        return "Hei Maailma!";
    }
}