package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.controller.UsuariForm;
import deim.urv.cat.homework2.model.Usuari;

public interface UsuariService {
    public Usuari authenticate(String usuari, String contrassenya);
    public boolean addUser(UsuariForm user);
}
