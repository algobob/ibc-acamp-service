package com.ibc.acamp.acampantecrud;

public class AcamapanteValidator implements AcampValidator{
    private final Acampante acampante;

    public AcamapanteValidator(Acampante acampante) {
        this.acampante = acampante;
    }

    @Override
    public void validate() throws AcampanteInvalidoException {
        if (isNullOrEmptyField(acampante.getNome()))
            throw new AcampanteInvalidoException("Nome do acampante é obrigatório.");

        if (isNullOrEmptyField(acampante.getSexo()))
            throw new AcampanteInvalidoException("Sexo do acampante é obrigatório.");
    }

    private boolean isNullOrEmptyField(String value) {
        return value == null || value.isEmpty();
    }

}
