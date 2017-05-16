package com.ibc.acamp.acampantecrud;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
@Getter
public class Acampante {
    private String nome;
    private int idade;
    private String sexo;
}
