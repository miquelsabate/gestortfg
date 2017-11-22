package cat.urv.deim.sob;

public class User {

    private String nomUsuari;
    private String pass;
    private String nomComplet;
    private String tipus;

    public User(String id, String pass, String nom) {
        nomUsuari = id;
        this.pass = pass;
        nomComplet = nom;
    }

    public String getNomUsuari() {
        return fixNull(this.nomUsuari);
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getPass() {
        return fixNull(this.pass);
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNomComplet() {
        return fixNull(this.nomComplet);
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getTipus() {
        return fixNull(this.tipus);
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }

    public String getMessage() {

        return "\nFirst Name: " + getNomUsuari() + "\n"
                + "Last Name:  " + getPass() + "\n"
                + "Email:      " + getNomComplet() + "\n";
    }
}
