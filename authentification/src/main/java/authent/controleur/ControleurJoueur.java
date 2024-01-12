package authent.controleur;


import authent.dto.AuthDTO;
import authent.dto.JoueurDTO;
import authent.facade.FacadeJoueur;
import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import authent.modele.PseudoDejaPrisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;


@RestController
@RequestMapping("/authent")
@EnableWebSecurity
public class ControleurJoueur {
    private static final Object TOKEN_PREFIX = "Bearer ";
    @Autowired
    FacadeJoueur facadeJoueurs;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Function<Joueur,String> genereToken;



    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscription( @RequestBody JoueurDTO joueurDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(joueurDTO.password());
            Joueur j = facadeJoueurs.inscription(joueurDTO.email(), joueurDTO.login(), encodedPassword);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{pseudo}")
                    .buildAndExpand(joueurDTO.login()).toUri();
            return ResponseEntity.created(location).header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX+genereToken.apply(j)).build();
        } catch (PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pseudo "+joueurDTO.login()+" déjà pris");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login( @RequestBody AuthDTO joueurDTO) {
        Joueur j = null;
        try {
            j = facadeJoueurs.getJoueurByPseudo(joueurDTO.login());
        } catch (JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (passwordEncoder.matches(joueurDTO.password(), j.getMdpJoueur())) {
            String token = genereToken.apply(j);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+token).build();
        };
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/inscription/{pseudo}")
    public ResponseEntity<JoueurDTO> get(@PathVariable String pseudo) {
        Joueur j = null;
        try {
            j = facadeJoueurs.getJoueurByPseudo(pseudo);
        } catch (JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new JoueurDTO(j.getEmail(),j.getLogin(),j.getMdpJoueur()));
    }

    @DeleteMapping(value = "/inscription/{pseudo}")
    public ResponseEntity<String> desinscription(@PathVariable String pseudo) {
        try {
            this.facadeJoueurs.desincription(pseudo);
        } catch (JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mauvaises informations transmises !");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
