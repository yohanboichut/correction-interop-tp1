package pileouface.controleur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pileouface.facade.FacadeParties;
import pileouface.modele.Joueur;
import pileouface.modele.MauvaisIdentifiantConnexionException;
import pileouface.modele.Partie;
import pileouface.modele.Statistiques;

import java.util.Collection;

@RestController
@RequestMapping(value = "/jeu",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControleurParties {
    @Autowired
    FacadeParties facadeParties;

    @GetMapping(value = "/")
    public ResponseEntity<String> qui(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }

    @PostMapping(value = "/partie")
    public ResponseEntity<Partie> jouerPartie(@RequestParam String prediction, Authentication authentication){
        String identifiant = authentication.getName();
        Joueur j = facadeParties.getOrCreateJoueur(identifiant);
        Partie p = j.jouer(prediction);
        return ResponseEntity.ok(p);
    }

    @GetMapping(value = "/partie")
    public ResponseEntity<Collection<Partie>> getParties(Authentication authentication) {
        try {
            Collection<Partie> parties = this.facadeParties.getAllParties(authentication.getName());
            return ResponseEntity.ok(parties);
        } catch (MauvaisIdentifiantConnexionException e) {
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/statistiques")
    public ResponseEntity<Statistiques> getStatistiques(Authentication authentication) {
        try {
            Statistiques statistiques = this.facadeParties.getStatistiques(authentication.getName());
            return ResponseEntity.ok(statistiques);
        } catch (MauvaisIdentifiantConnexionException e) {}
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping(value = "/joueur/{pseudo}")
    public ResponseEntity<String> supprimerJoueur(@PathVariable String pseudo){
        this.facadeParties.suppressionJoueur(pseudo);
        return ResponseEntity.ok("Joueur supprimé");
    }

}
