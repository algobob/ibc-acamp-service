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
    private Integer id;
    private String nome;
    private Integer idade;
    private String sexo;
}
