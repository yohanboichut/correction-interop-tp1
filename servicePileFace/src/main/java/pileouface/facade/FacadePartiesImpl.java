package pileouface.facade;

import org.springframework.stereotype.Component;
import pileouface.modele.Joueur;
import pileouface.modele.MauvaisIdentifiantConnexionException;
import pileouface.modele.Partie;
import pileouface.modele.Statistiques;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("facadeParties")
public class FacadePartiesImpl implements FacadeParties {
    /**
     * Dictionnaire des joueurs connectés indexés par leur pseudo
     */
    private Map<String, Joueur> joueursActuels;

    public FacadePartiesImpl() {
        this.joueursActuels = new HashMap<>();
    }

    private void checkIdConnexion(String idConnexion) throws MauvaisIdentifiantConnexionException {
        if (!this.joueursActuels.containsKey(idConnexion))
            throw new MauvaisIdentifiantConnexionException();
    }

    @Override
    public Partie jouer(String pseudo, String choix) throws MauvaisIdentifiantConnexionException {
        this.checkIdConnexion(pseudo);
        Joueur j = this.joueursActuels.get(pseudo);
        Partie partie = j.jouer(choix);
        return partie;
    }

    /**
     * Permet de récupérer les statistiques d'un utilisateur connecté
     * @param pseudo
     * @return
     * @throws MauvaisIdentifiantConnexionException
     */
    @Override
    public Statistiques getStatistiques(String pseudo) throws MauvaisIdentifiantConnexionException {
        this.checkIdConnexion(pseudo);
        Joueur j = this.joueursActuels.get(pseudo);
        int nb = j.getNbPartiesJouees();
        double ratio = (double)this.getOrCreateJoueur(pseudo).getNbPartiesGagnees()/((double)nb);
        return new Statistiques(nb,ratio);
    }

    /**
     * Permet de récupérer l'historique des parties d'un joueur connecté
     * @param pseudo
     * @return
     * @throws MauvaisIdentifiantConnexionException
     */
    @Override
    public Collection<Partie> getAllParties(String pseudo) throws MauvaisIdentifiantConnexionException {
        Joueur j = getOrCreateJoueur(pseudo);
        return this.joueursActuels.get(pseudo).getHistorique();
    }

    @Override
    public Joueur getOrCreateJoueur(String pseudo) {
        if (joueursActuels.containsKey(pseudo)){
            return joueursActuels.get(pseudo);
        }
        Joueur j = new Joueur(pseudo);
        joueursActuels.put(pseudo,j);
        return j;
    }

    @Override
    public void suppressionJoueur(String pseudo) {
        this.joueursActuels.remove(pseudo);
    }

}