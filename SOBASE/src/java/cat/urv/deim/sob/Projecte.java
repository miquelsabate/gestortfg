package cat.urv.deim.sob;

public class Projecte {

    private String titol;
    private String estat;
    private String professor;
    private float qualificacio;
    
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

    public String getMessage() {

        return "\nFirst Name: " + getTitol() + "\n"
                + "Last Name:  " + getEstat() + "\n"
                + "Email:      " + getProfessor() + "\n";
    }
}
