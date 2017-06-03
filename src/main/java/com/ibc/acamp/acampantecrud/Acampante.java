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
    private final Integer id;
    private final String nome;
    private final int idade;
    private final String sexo;
}
