package com.quesito.controller;

import com.quesito.model.Player;
import com.quesito.model.Response;
import com.quesito.model.Rooster;
import com.quesito.repository.Memory;
import com.quesito.service.PlayerService;
import com.quesito.service.RoosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoosterController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private RoosterService roosterService;

    @RequestMapping("/rooster")
    public String rooster(Model model) {
        defaultModel(model);
        return "rooster";
    }



    @RequestMapping(value = "/selectUser", method = RequestMethod.POST)
    public String addRooster(Model model, @ModelAttribute Player selectPlayer) {
        defaultModel(model);
        if (selectPlayer.getName().equals("default")) {
            return "rooster";
        }
        Response response = new Response();
        response.setRooster(new Rooster());
        model.addAttribute("numGroups", Memory.numberOfTeams);
        Player player = playerService.findPlayer(selectPlayer.getName());
        response.setPlayerName(player.getName());
        model.addAttribute("selectedPlayer", player);
        model.addAttribute("response", response);
        return "rooster";
    }

    @RequestMapping(value = "/addRooster", method = RequestMethod.POST)
    public String addRooster(Model model, @ModelAttribute Response responseR) {
        System.out.println(responseR);
        model.addAttribute("numGroups", Memory.numberOfTeams);
        model.addAttribute("players", Memory.players);
        Response response = new Response();
        response.setRooster(new Rooster());
        if (roosterService.safeToSave(responseR)) {
            roosterService.saveRooster(responseR);
        }else{
            model.addAttribute("error","El Grupo "+responseR.getGroupId()+" esta lleno prueba con otro");
        }
        Player player = playerService.findPlayer(responseR.getPlayerName());
        response.setPlayerName(player.getName());
        model.addAttribute("selectedPlayer", player);
        model.addAttribute("response", response);
        return "rooster";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/deleteRooster")
    public String deleteRooster(Model model, @RequestParam("group") Integer group,
                                @RequestParam("name") String name,
                                @RequestParam("idMarchamo") String idMarchamo ,
                                @RequestParam("weight") Double weight){
        Rooster rooster=new Rooster(idMarchamo,weight);
        Response rs=new Response(name,rooster,group);
        roosterService.deleteRooster(rs);
        model.addAttribute("numGroups", Memory.numberOfTeams);
        model.addAttribute("players", Memory.players);
        Response response = new Response();
        response.setRooster(new Rooster());
        Player player = playerService.findPlayer(name);
        response.setPlayerName(player.getName());
        model.addAttribute("selectedPlayer", player);
        model.addAttribute("response", response);

        return "rooster";

    }






    public void defaultModel(Model model) {
        model.addAttribute("players", Memory.players);
        model.addAttribute("selectedPlayer", new Player());
    }

}
