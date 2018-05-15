package com.ibc.acamp.acampantecrud;

import java.util.List;

public interface IAcampanteService {

    boolean save(Acampante acampante);
    List<Acampante> fetch();
    boolean update(Acampante acampante);

}
