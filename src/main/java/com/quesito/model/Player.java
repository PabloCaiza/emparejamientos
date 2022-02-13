package com.quesito.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Player {

    private String name;
    private List<Group> groups;
}
