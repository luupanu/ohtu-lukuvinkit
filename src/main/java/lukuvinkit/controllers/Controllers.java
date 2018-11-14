package lukuvinkit.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lukuvinkit.models.Lukuvinkki;

// this can be turned into lukuvinkkicontroller if needed
@Controller
public class Controllers {
    private ArrayList<Lukuvinkki> list;

    // dummy before database setup
    public Controllers() {
        Lukuvinkki dummy = new Lukuvinkki("esim", "www.esim.com", "esim esim esim");
        this.list = new ArrayList<Lukuvinkki>();
        this.list.add(dummy);
    }

    @GetMapping("/")
    @ResponseBody
    public String home(Model model) {
        model.addAttribute("list", list);
        return "index";
    }

    @GetMapping("/other")
    @ResponseBody
    public String other() {
        return "Hei Maailma!";
    }
}