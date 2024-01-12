package authent.facade;

import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import authent.modele.PseudoDejaPrisException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("facadeJoueurs")
public class FacadeJoueurImpl implements FacadeJoueur {


    /**
     * Dictionnaire des joueurs inscrits
     */
    private Map<String, Joueur> joueurs;


    public FacadeJoueurImpl() {
        this.joueurs = new HashMap<>();
    }


    @Override
    public Joueur inscription(String email, String pseudo, String mdp) throws PseudoDejaPrisException {
        if (joueurs.containsKey(pseudo))
            throw new PseudoDejaPrisException();
        Joueur v = new Joueur(email, pseudo,mdp);
        this.joueurs.put(pseudo, v);
        return v;
    }

    @Override
    public Joueur getJoueurByPseudo(String idJoueur)  throws JoueurInexistantException {
        if (!joueurs.containsKey(idJoueur)) {
            throw new JoueurInexistantException();
        }
        return joueurs.get(idJoueur);
    }

    @Override
    public void desincription(String pseudo) throws JoueurInexistantException {
        if (!joueurs.containsKey(pseudo))
            throw new JoueurInexistantException();
        joueurs.remove(pseudo);
    }



}
