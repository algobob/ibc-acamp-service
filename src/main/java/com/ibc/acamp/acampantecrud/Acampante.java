package com.ibc.acamp.acampantecrud;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
public class Acampante {
    private String nome;
    private int idade;
    private String sexo;
}
