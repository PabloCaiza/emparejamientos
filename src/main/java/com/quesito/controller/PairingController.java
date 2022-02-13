package com.quesito.controller;

import com.quesito.model.Round;
import com.quesito.repository.Memory;
import com.quesito.service.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PairingController {

    @Autowired
    private PairingService pairingService;

    @RequestMapping("/pairing")
    public String pairing() {
        return "pairing";
    }
    @RequestMapping(value = "/calculatePairing",method = RequestMethod.POST)
    public String calculatePairing(Model model) {
        if(Memory.players.size()<2){
            model.addAttribute("error","Deben existir al menos dos jugadores.");
            return "pairing";
        }
        List<Round> rounds = pairingService.getRounds();
        if(rounds==null){
            model.addAttribute("error","Existe jugadores sin marchamos. ");
            return "pairing";
        }
        if(rounds.size()==0){
            model.addAttribute("error","Vuelvalo a intentar ");
            return "pairing";
        }
          model.addAttribute("rounds",rounds);
        return "pairing";
    }


}
