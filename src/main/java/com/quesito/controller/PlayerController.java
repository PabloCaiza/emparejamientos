package com.quesito.controller;

import com.quesito.model.Player;
import com.quesito.repository.Memory;
import com.quesito.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Member;

@Controller
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/player")
    public String player(Model model){
        model.addAttribute("player",new Player());
        model.addAttribute("players", Memory.players);
        return "player";
    }
    @RequestMapping(value = "/addPlayer",method = RequestMethod.POST)
    public String addPlayer(@ModelAttribute Player player,Model model){
        Player savePlayer = playerService.addPlayer(player);
        if(savePlayer!=null){
            model.addAttribute("player",new Player());
        }else{
            model.addAttribute("player",player);
            model.addAttribute("error","ya existe ese jugador, ingrese otro nombre");
        }
        model.addAttribute("players", Memory.players);

        return "player";
    }



}
