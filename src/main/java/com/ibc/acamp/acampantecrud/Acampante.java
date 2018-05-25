package com.ibc.acamp.acampantecrud;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@DatabaseTable(tableName = "acampantes")
@NoArgsConstructor
@AllArgsConstructor
public class Acampante {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String nome;
    @DatabaseField
    private Integer idade;
    @DatabaseField(canBeNull = false)
    private String sexo;
}
