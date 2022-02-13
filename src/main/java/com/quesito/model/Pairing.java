package com.quesito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pairing {
    private Rival rival1;
    private  Rival rival2;
}
