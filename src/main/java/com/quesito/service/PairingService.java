package com.quesito.service;

import com.quesito.model.*;
import com.quesito.repository.Memory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PairingService {
    @Autowired
    private RivalService rivalService;


    public List<Round> getRounds() {
        List<Rival> rivals = rivalService.getRivals(Memory.players);
        int totalNumberOfRivals = rivals.size();
        if (!isComplete(totalNumberOfRivals)) {
            return null;
        }

        if (totalNumberOfRivals % 2 != 0) {
            addNewRival(rivals);
            totalNumberOfRivals++;
        }
        List<Pairing> pairings = getPairings(rivals);
        if (pairings.size() == 0) {
            return new ArrayList<Round>();
        }
        List<Round> rounds = new ArrayList<>();
        int totalRounds = determineRounds();
        int numberOfPairingPerRound = determinarNumeroEmparejamientos(totalRounds, totalNumberOfRivals);
        int index = 0;
//        for (int i = 0; i < totalRounds; i++) {
//            Round round = new Round();
//            round.setNumber(i + 1);
//            if (i == totalRounds - 1) {
//                round.setPairings(pairings.subList(index, pairings.size()));
//            } else {
//                round.setPairings(pairings.subList(index, index + numberOfPairingPerRound));
//            }
//            index += numberOfPairingPerRound;
//            rounds.add(round);
//
//        }
        obtainRounds(rounds,pairings,numberOfPairingPerRound,totalRounds);

        return rounds;

    }

    public void obtainRounds(List<Round> rounds,List<Pairing> pairings,int numberOfPairingPerRound,int totalRounds) {
        for (int j = 0; j < totalRounds; j++) {
            Round round=new Round();
            round.setNumber(j + 1);
            if (j == totalRounds - 1) {
               round.setPairings(pairings);
            } else {
                List<Pairing> newPairingList = new ArrayList<>();
                for (int i = 0; i < numberOfPairingPerRound; i++) {
                    if (i == 0) {
                        newPairingList.add(pairings.get(0));
                        pairings.remove(0);
                    } else {
                        newPairingList.add(seachAnotherPairing(newPairingList, pairings));


                    }

                }
                round.setPairings(newPairingList);
            }
            rounds.add(round);

        }
    }

    public Pairing seachAnotherPairing(List<Pairing> newPairingList, List<Pairing> pairings) {
        for (int i = 0; i < pairings.size(); i++) {
            int cont=0;
            for (int j = 0; j < newPairingList.size(); j++) {
                if (pairings.get(i).getRival1().getTeam() != newPairingList.get(j).getRival1().getTeam() &&
                        pairings.get(i).getRival2().getTeam() != newPairingList.get(j).getRival2().getTeam()
                ){
                    cont++;
                }
            }
            if(cont==newPairingList.size()){
                 Pairing pairing=pairings.get(i);
                 pairings.remove(i);
                 return  pairing;
            }
        }
        Pairing pairing = pairings.get(0);
        pairings.remove(0);
        return pairing;

    }

    public List<Pairing> getPairings(List<Rival> rivals) {
        List<Pairing> pairings = new ArrayList<>();
        createPairings(rivals, 0, pairings);
        return pairings;
    }

    public void addNewRival(List<Rival> rivals) {
        rivals.add(new Rival("Casa", 1, "xxx", 4.5, false, new ArrayList<>()));

    }

    public int determinarNumeroEmparejamientos(int rondas, int total) {
        return (int) ((total / 2) / rondas);
    }

    public int determineRounds() {
        return Memory.numberOfRoosters;
    }

    public boolean isComplete(int number) {
        return number == Memory.numberOfTeams.intValue() * Memory.numberOfRoosters.intValue() * Memory.players.size();
    }

    public boolean createPairings(List<Rival> rivals, int pos, List<Pairing> pairings) {

        for (int i = pos; i < rivals.size(); i++) {
            if (rivals.get(i).isBusy()) {
                continue;
            }
            Rival rival = searchRival(rivals, rivals.get(i).getRivals(), rivals.get(i));
            if (rival == null) {
                return false;
            }
            Pairing pairing = new Pairing(rivals.get(i), rival);
            changeState(rivals.get(i), rival);
            pairings.add(pairing);
            if (!createPairings(rivals, i + 1, pairings)) {
                changeState(rivals.get(i), rival);
                pairings.remove(pairing);
                rivals.get(i).getRivals().add(rival);
                i--;
            }
            if (pairings.size() == calculateNumberOfPairings(rivals.size())) {
                break;
            }


        }
        return true;
    }

    public int calculateNumberOfPairings(int total) {
        return (int) total / 2;
    }

    public void changeState(Rival rival1, Rival rival2) {
        rival1.setBusy(!rival1.isBusy());
        rival2.setBusy(!rival2.isBusy());
    }

    public Rival searchRival(List<Rival> rivals, List<Rival> rivalsNotAllowed, Rival rival) {
        List<Rival> selectedRivals = rivals.stream()
                .filter(r -> !r.getName().equalsIgnoreCase(rival.getName()))
                .filter(r -> {
                    return !rivalsNotAllowed.stream().anyMatch(rival1 -> rival1 == r);
                })
                .filter(r -> !r.isBusy())
                .collect(Collectors.toList());
        if (selectedRivals.size() == 0) {
            return null;
        }

        return selectedRivals.stream()
                .filter(r -> rival.getWeight() >= r.getWeight() - 0.2 && rival.getWeight() <= r.getWeight() + 0.2)
                .findFirst()
                .orElse(selectedRivals.get((int) (Math.random() * (selectedRivals.size() - 0))));
//        return  selectedRivals.get((int)(Math.random()*(selectedRivals.size()-0)));


    }

}
