package cat.urv.deim.sob;

import java.sql.Date;

public class Projecte {

    private String titol;
    private String estat;
    private String professor;
    private float qualificacio;
    private Date data_def, data_crea, data_mod;
    private String descripcio, recursos, estudiant, estudi;

    public Projecte(String titol, String estat, String professor){
        this.titol=titol;
        this.estat=estat;
        this.professor=professor;
    }

    public String getTitol() {
        return fixNull(this.titol);
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getEstat() {
        return fixNull(this.estat);
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getProfessor() {
        return fixNull(this.professor);
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public float getQualificacio() {
        return (this.qualificacio);
    }

    public void setQualificacio(float qualificacio) {
        this.qualificacio = qualificacio;
    }
    
    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
    
    public Date getData_def() {
        return data_def;
    }

    public Date getData_crea() {
        return data_crea;
    }

    public Date getData_mod() {
        return data_mod;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setData_def(Date data_def) {
        this.data_def = data_def;
    }

    public void setData_crea(Date data_crea) {
        this.data_crea = data_crea;
    }

    public void setData_mod(Date data_mod) {
        this.data_mod = data_mod;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }
    
    public String getEstudiant() {
        return estudiant;
    }

    public void setEstudiant(String estudiant) {
        this.estudiant = estudiant;
    }
    
    public String getEstudi() {
        return estudi;
    }

    public void setEstudi(String estudi) {
        this.estudi = estudi;
    }
    
}
