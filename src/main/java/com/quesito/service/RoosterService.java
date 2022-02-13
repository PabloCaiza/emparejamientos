package com.quesito.service;


import com.quesito.model.Player;
import com.quesito.model.Response;
import com.quesito.model.Rooster;
import com.quesito.repository.Memory;
import org.springframework.stereotype.Service;

@Service
public class RoosterService {
    public void deleteRooster(Response response){
        Memory.players.stream()
                .filter(player -> player.getName().equals(response.getPlayerName()))
                .findAny()
                .get()
                .getGroups()
                .stream()
                .filter(group -> group.getId()==response.getGroupId())
                .findAny()
                .get()
                .getRoosters()
                .removeIf(rooster -> rooster.getId().equals(response.getRooster().getId()) && rooster.getWeight()==response.getRooster().getWeight());
    }

    public void saveRooster(Response response){
            Memory.players.stream()
                    .filter(player -> player.getName().equals(response.getPlayerName()))
                    .findAny()
                    .get()
                    .getGroups()
                    .stream()
                    .filter(group -> group.getId().intValue()==response.getGroupId())
                    .findAny()
                    .get()
                    .getRoosters()
                    .add(response.getRooster());


    }
    public boolean safeToSave(Response response){
      return Memory.players.stream()
              .filter(player -> player.getName().equals(response.getPlayerName()))
              .findAny()
              .get()
              .getGroups()
              .stream()
              .filter(group -> group.getId().intValue()==response.getGroupId())
              .findAny()
              .get()
              .getRoosters()
              .size()!=Memory.numberOfRoosters;



    }

}
