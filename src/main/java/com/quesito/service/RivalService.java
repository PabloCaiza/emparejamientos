package com.quesito.service;

import com.quesito.model.Player;
import com.quesito.model.Rival;
import com.quesito.repository.Memory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RivalService {

    public List<Rival> getRivals(List<Player> players){
        List<Rival> rivals=new ArrayList<>();
        players.stream()
                .forEach(player -> {
                    player.getGroups().forEach(group -> {
                        group.getRoosters().forEach(rooster -> {
                            rivals.add(new Rival(player.getName(),
                                    group.getId(),rooster.getId(),
                                    rooster.getWeight(),
                                    false,new ArrayList<>()));

                        });
                    });

                });

        return rivals;
    }
}
