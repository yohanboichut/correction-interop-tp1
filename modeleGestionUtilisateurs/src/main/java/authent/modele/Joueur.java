package authent.modele;

public class Joueur {

    private String email;
    private String mdpJoueur;
    private String login;

    public Joueur(String email, String login, String mdpJoueur) {
        this.email = email;
        this.mdpJoueur = mdpJoueur;
        this.login = login;
    }


    public String getEmail() {
        return email;
    }

    public String getMdpJoueur() {
        return mdpJoueur;
    }

    public String getLogin() {
        return login;
    }
}
