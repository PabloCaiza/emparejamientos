package com.quesito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rival {
    private String name;
    private int team;
    private String marchamo;
    private double weight;
    private boolean isBusy;
    private List<Rival> rivals;
}
