package authent.facade;

import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import authent.modele.PseudoDejaPrisException;

public interface FacadeJoueur {
    /**
     * Inscription d'un nouveau joueur à la POFOL
     *
     * @param email
     * @param mdp
     * @return
     * @throws PseudoDejaPrisException
     */
    Joueur inscription(String email,String pseudo, String mdp) throws PseudoDejaPrisException;

    Joueur getJoueurByPseudo(String idJoueur) throws JoueurInexistantException;


    /**
     * Permet de se désinscrire de la plate-forme
     *
     * @param pseudo
     * @throws JoueurInexistantException
     */
    void desincription(String pseudo) throws JoueurInexistantException;


}
