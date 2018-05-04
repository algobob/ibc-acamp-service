package com.ibc.acamp.acampantecrud;

import java.util.List;

public interface AcampanteServiceInterface {

    boolean save(Acampante acampante);
    List<Acampante> fetch();
    boolean update(Acampante acampante);

}
