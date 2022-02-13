package com.quesito.controller;

import com.quesito.model.Convention;
import com.quesito.repository.Memory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class ConventionController {

    @RequestMapping("/")
    public String convention(Model model){
        model.addAttribute("convention",new Convention(Memory.numberOfRoosters,Memory.numberOfTeams));
        return "convention";
    }
    @RequestMapping(value = "/addConvention",method = RequestMethod.POST)
    public String submit(@ModelAttribute Convention convention,Model model){
        Memory.numberOfRoosters=convention.getNumberOfRoosters();
        Memory.numberOfTeams=convention.getNumberOfTeams();
        Memory.players=new ArrayList<>();
        model.addAttribute("message","Se estableció las reglas del juego.");
        return "convention";
    }
}
