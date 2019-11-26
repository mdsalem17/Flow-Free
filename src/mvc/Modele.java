/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import cases.CaseSymbol;
import cases.Case;
import java.util.Observable;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import jeu.JeuLignes;

/**
 *
 * @author fred
 */
public class Modele extends Observable {
    
    public int firstC, firstR;
    public int currentC, currentR;
    public int lastC, lastR;
    JeuLignes jeu;
    Color couleur[], backgroundCouleur[];
    CaseSymbol caseDebutCourant;
    boolean canBeDragged;
    
    public Modele(){
        jeu = new JeuLignes();
        
        couleur = new Color[7];
        couleur[0] = Color.TRANSPARENT;
        couleur[1] = Color.BLUE;
        couleur[2] = Color.GREEN;
        
        backgroundCouleur = new Color[7];
        backgroundCouleur[0] = Color.TRANSPARENT;
        backgroundCouleur[1] = new Color(0, 0, 0.4, 1);
        backgroundCouleur[2] = new Color(0, 0.2, 0, 1);
        
        canBeDragged = true;
    }
    
    public void startDD(int c, int r) {
        // TODO
        System.out.println("startDD : " + r + "-" + c);
        firstC = c;
        firstR = r;
        currentC = c;
        currentR = r;
        setChanged();
        caseDebutCourant = new CaseSymbol(jeu.grille.getCase(r, c).getId(), r, c);
        for(int i = 0 ; i < jeu.grille.getTaille(); i++){
            for(int j = 0; j < jeu.grille.getTaille(); j++){
                if(jeu.grille.isChemin(i, j) && (caseDebutCourant.getId() == -jeu.grille.getCase(i, j).getId()))
                    jeu.grille.setCaseId(0, i, j);
                    jeu.tabChemins[caseDebutCourant.getId()-1].viderChemin();
            }
        }
        notifyObservers();
    }
    
    public void stopDD(int c, int r) {
        // TODO
        
        // mémoriser le dernier objet renvoyé par parcoursDD pour connaitre la case de relachement
        
        System.out.println("stopDD : " + r + "-" + c + " -> " + lastR + "-" + lastC);
        
        if(currentR != r || currentC != c){
            jeu.tabChemins[caseDebutCourant.getId()-1].ajouter(jeu.tabChemins[caseDebutCourant.getId()-1].getTrajetSize(), jeu.grille.getCase(currentR, currentC));
            jeu.tabChemins[caseDebutCourant.getId()-1].removeDuplicate();
            jeu.modifTrajet(caseDebutCourant, canBeDragged);
            jeu.appliquerChemin();
            /*
            if(jeu.grille.getCase(currentR, currentR).getId() != caseDebutCourant.getId() && jeu.grille.isSymbol(r, c)){
                jeu.tabChemins[caseDebutCourant.getId()-1].modifierChemin(new Case(jeu.grille.getCase(currentR, currentC).getId(), currentR, currentC));
            }*/
            currentR = r;
            currentC = c;
        }
        canBeDragged = true;
        setChanged();
        notifyObservers();

        System.out.println(jeu.grille.toString());
        System.out.println("partie gagner "+jeu.partieTerminee());
        System.out.print("Trajet Size = "+jeu.tabChemins[caseDebutCourant.getId()-1].getTrajetSize()+"\n[");
        for(int i = 0; i < jeu.tabChemins[caseDebutCourant.getId()-1].getTrajetSize() ; i++){
            System.out.print("( ("+jeu.tabChemins[caseDebutCourant.getId()-1].getCaseTrajet(i).getId()+"), "+jeu.tabChemins[caseDebutCourant.getId()-1].getCaseTrajet(i).getX()+", "+jeu.tabChemins[caseDebutCourant.getId()-1].getCaseTrajet(i).getY()+"), ");
        }
        System.out.print("]");
    }
    
    public void parcoursDD(int c, int r) {
        // TODO
        lastC = c;
        lastR = r;
        System.out.println("parcoursDD : " + r + "-" + c);
        if(canBeDragged && jeu.grille.isChemin(currentR, currentC) &&
                (jeu.grille.getCase(currentR, currentC).getId() == 0 || Math.abs(jeu.grille.getCase(currentR, currentC).getId()) == caseDebutCourant.getId() )){
            jeu.grille.setCaseId(-caseDebutCourant.getId(), currentR, currentC);
        }else if(Math.abs(jeu.grille.getCase(currentR, currentC).getId()) != caseDebutCourant.getId()){
            canBeDragged = false;
        }
        if(currentR != r || currentC != c){
            jeu.tabChemins[caseDebutCourant.getId()-1].ajouter(jeu.tabChemins[caseDebutCourant.getId()-1].getTrajetSize(), jeu.grille.getCase(currentR, currentC));
            jeu.tabChemins[caseDebutCourant.getId()-1].removeDuplicate();
            jeu.modifTrajet(caseDebutCourant, canBeDragged);
            jeu.appliquerChemin();
            currentR = r;
            currentC = c;
        }
        System.err.println("canBeDragged => "+canBeDragged);
        setChanged();
        notifyObservers();
    }
    
    public boolean isRight(int x1, int y1, int x2, int y2){
        return (x1 < x2 && y1 == y2);
    }
    
    public boolean isLeft(int x1, int y1, int x2, int y2){
        return (x1 > x2 && y1 == y2);
    }
    
}