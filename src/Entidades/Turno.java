package Entidades;

import DAO.DAOIBajarTurno;
import Services.DAOBajarTurno;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private Medico medico;
    private Paciente paciente;
    private LocalDate fecha;
    private LocalTime Hora;
    private String lugar;
    private int consultorio;
    private int estado;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return Hora;
    }

    public void setHora(LocalTime hora) {
        Hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(int consultorio) {
        this.consultorio = consultorio;
    }

    @Override
    public String toString() {
        String est = "";
        if (this.estado == 1){
            est = "En espera";
        }
        if (this.estado == 2){
            est = "Tomado";
        }

        return "Turno{" + "\n" +
                "medico: " + medico +"\n" +
                "paciente: " + paciente +"\n" +
                "fecha: " + fecha + "\n" +
                "Hora: " + Hora + "\n" +
                "lugar: " + lugar  +"\n" +
                "consultorio: " + consultorio + "\n"+
                "Estado: " + est +"\n"+
                '}' +"\n";
    }

    public static void bajarTurno(Turno t){
        DAOIBajarTurno bt = new DAOBajarTurno();
        int pid = Paciente.getIdPaciente(t.paciente.getDni());
        int mid = Medico.getId(t.medico.getDni());
        String lugar = t.lugar;
        LocalDate fecha = t.fecha;
        LocalTime time = t.Hora;
        int consul = t.consultorio;
        bt.darBaja(mid,pid,lugar,fecha,time,consul);
    }
}
