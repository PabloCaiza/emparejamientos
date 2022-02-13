package com.quesito.service;

import com.quesito.model.Group;
import com.quesito.model.Player;
import com.quesito.repository.Memory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlayerService {

    public Player addPlayer(Player player){
        if(playerExist(player)){
            return null;
        }
        player.setGroups(addGroups());
        Memory.players.add(player);
        return player;
    }
    public List<Group> addGroups(){
        List<Group> groups=new ArrayList<>();
        for (int i = 0; i < Memory.numberOfTeams; i++) {
            Group g = new Group();
            g.setId(i+1);
            g.setRoosters(new ArrayList<>());
            groups.add(g);
        }
        return  groups;
    }
    public boolean playerExist(Player player){
        return Memory.players.stream()
                .anyMatch(player1 -> player1.getName().equalsIgnoreCase(player.getName()));
    }
    public Player findPlayer(String name){
        return  Memory.players.stream()
                .filter(player -> player.getName().equalsIgnoreCase(name))
                .findFirst().get();
    }

}
