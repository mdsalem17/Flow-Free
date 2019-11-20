/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import cases.CaseSymbol;
import java.util.Observable;
import javafx.scene.paint.Color;
import jeu.JeuLignes;

/**
 *
 * @author fred
 */
public class Modele extends Observable {
    
    public int firstC, firstR;
    public int lastC, lastR;
    JeuLignes jeu;
    Color couleur[];
    CaseSymbol caseDebutCourant;
    
    public Modele(){
        jeu = new JeuLignes();
        
        couleur = new Color[7];
        couleur[0] = Color.TRANSPARENT;
        couleur[1] = Color.BLUE;
        couleur[2] = Color.GREEN;
    }
    
    public void startDD(int c, int r) {
        // TODO
        System.out.println("startDD : " + r + "-" + c);
        firstC = c;
        firstR = r;
        setChanged();
        caseDebutCourant = new CaseSymbol(jeu.grille.getCase(r, c).getId(), r, c);
        for(int i = 0 ; i < jeu.grille.getTaille(); i++){
            for(int j = 0; j < jeu.grille.getTaille(); j++){
                if(jeu.grille.isChemin(i, j) && (caseDebutCourant.getId() == -jeu.grille.getCase(i, j).getId()))
                    jeu.grille.setCaseId(0, i, j);
            }
        }
        notifyObservers();
    }
    
    public void stopDD(int c, int r) {
        // TODO
        
        // mémoriser le dernier objet renvoyé par parcoursDD pour connaitre la case de relachement
        
        System.out.println("stopDD : " + r + "-" + c + " -> " + lastR + "-" + lastC);
        System.out.println(jeu.grille.toString());
        setChanged();
        notifyObservers();
        if((caseDebutCourant.getId() == jeu.grille.getCase(lastR, lastC).getId()) || jeu.grille.getCase(lastR, lastC).getId() < 0){
            //jeu.setCheminCourant(caseDebutCourant, jeu.grille.getCase(r, c));
            
            System.out.println("chemin etabli");
        }
        System.out.println("partie gagner "+jeu.partieTerminee());
    }
    
    public void parcoursDD(int c, int r) {
        // TODO
        lastC = c;
        lastR = r;
        System.out.println("parcoursDD : " + r + "-" + c);
        if(jeu.grille.isChemin(r, c)){
            jeu.grille.setCaseId(-caseDebutCourant.getId(), r, c);
        }
        setChanged();
        notifyObservers();
    }
    
    
   

}