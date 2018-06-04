package com.ibc.acamp.acampantecrud;

import org.junit.Test;

public class AcampanteValidatorTest {

    @Test( expected = AcampanteInvalidoException.class)
    public void validateAcampanteName() throws AcampanteInvalidoException {
        Acampante acampante = Acampante.builder().sexo("masc").idade(14).build();
        new AcamapanteValidator(acampante).validate();

    }

    @Test( expected = AcampanteInvalidoException.class)
    public void validateAcampanteSexo() throws AcampanteInvalidoException {
        Acampante acampante = Acampante.builder().nome("nomeee").idade(14).build();
        new AcamapanteValidator(acampante).validate();

    }
}
