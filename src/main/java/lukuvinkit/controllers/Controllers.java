package lukuvinkit.controllers;

import java.util.ArrayList;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;

import lukuvinkit.models.Lukuvinkki;

// this can be turned into lukuvinkkicontroller if needed
@Controller
public class Controllers {
    private ArrayList<Lukuvinkki> list;

    // dummy before database setup
    @Autowired
    public Controllers() {
        Lukuvinkki dummy = new Lukuvinkki("esim", "https://www.esim.com", "esim esim esim");
        this.list = new ArrayList<Lukuvinkki>();
        this.list.add(dummy);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("list", list);
        model.addAttribute("lukuvinkki", new Lukuvinkki());
        return "index";
    }

    @PostMapping(value = "/")
    public String post(@Valid @ModelAttribute Lukuvinkki lukuvinkki, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        this.list.add(lukuvinkki);
        return "redirect:/";
    }

    @GetMapping("/other")
    @ResponseBody
    public String other() {
        return "Hei Maailma!";
    }
}