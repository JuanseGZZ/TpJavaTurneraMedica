package DAO;

import Entidades.Paciente;

import java.util.List;

public interface DAOIPaciente {
    public int getId(int dni);
    public List<Paciente> getPacientes();
}
