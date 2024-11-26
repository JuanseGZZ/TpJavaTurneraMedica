package Entidades;

import Services.DAODisponibilidad;
import Services.DAOMedico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Disponibilidad {
    private Medico medico;
    private LocalDate desde;
    private LocalDate hasta;
    private Hospital hospital;
    private int consultorio;
    private LocalTime horadesde;
    private LocalTime horahasta;

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public int getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(int consultorio) {
        this.consultorio = consultorio;
    }

    public LocalTime getHoradesde() {
        return horadesde;
    }

    public void setHoradesde(LocalTime horadesde) {
        this.horadesde = horadesde;
    }

    public LocalTime getHorahasta() {
        return horahasta;
    }

    public void setHorahasta(LocalTime horahasta) {
        this.horahasta = horahasta;
    }

    static public Map<String,String> getHospitales(){
        DAODisponibilidad dd = new DAODisponibilidad();
        return dd.getHospitales();
    }

    static public Map<String,String> getMedicos(){
        DAODisponibilidad dd = new DAODisponibilidad();
        return dd.getMedicos();
    }

    static public Map<String,String> getConsultorios(String dni){
        DAODisponibilidad dd = new DAODisponibilidad();
        return dd.getConsultoriosPorHospital(dni);
    }

    static public int consolidar(int dniMedico,LocalDate desde,LocalDate hasta, String direccion,int concultorio,LocalTime desdeH,LocalTime hastaH){
        DAOMedico dm = new DAOMedico();
        int idmedico = dm.getId(dniMedico);
        DAODisponibilidad dd = new DAODisponibilidad();
        return dd.consolidar(idmedico,desde,hasta,direccion,concultorio,desdeH,hastaH);
    }


}
