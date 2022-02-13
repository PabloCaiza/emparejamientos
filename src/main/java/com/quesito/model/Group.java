package com.quesito.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private Integer id;
    private List<Rooster> roosters;
}
