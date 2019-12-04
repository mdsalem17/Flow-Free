/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import cases.CaseSymbol;
import java.util.Observable;
import jeu.JeuLignes;

/**
 *
 * @author Khaled
 */
public class Modele extends Observable {
    
    public int firstC, firstR;
    public int currentC, currentR;
    public int lastC, lastR;
    JeuLignes jeu;
    CaseSymbol caseDebutCourant;
    boolean canBeDragged;
    int caseDebutPassed;
    
    public Modele(int selectedGroup, int selectedLevel){
        jeu = new JeuLignes(selectedGroup, selectedLevel);
        canBeDragged = true;
        caseDebutPassed = 0;
    }
    
    public void startDD(int c, int r) {
        firstC = c;
        firstR = r;
        currentC = c;
        currentR = r;
        setChanged();
        caseDebutCourant = new CaseSymbol(jeu.grille.getCase(r, c).getId(), r, c);
        caseDebutPassed = 1;
        jeu.appliquerViderChemin(jeu.tabChemins[caseDebutCourant.getId()-1]);
        jeu.tabChemins[caseDebutCourant.getId()-1].viderChemin();
        notifyObservers();
    }
    
    public void parcoursDD(int c, int r) {
        lastC = c;
        lastR = r;
        if(canBeDragged && jeu.grille.isChemin(currentR, currentC) &&
                (jeu.grille.getCase(currentR, currentC).getId() == 0 || Math.abs(jeu.grille.getCase(currentR, currentC).getId()) == caseDebutCourant.getId() )){
            jeu.grille.setCaseId(-caseDebutCourant.getId(), currentR, currentC);
        }else if(Math.abs(jeu.grille.getCase(currentR, currentC).getId()) != caseDebutCourant.getId() || caseDebutPassed >= 2){
            canBeDragged = false;
        }
        if(!canBeDragged){
            return;
        }
        if(currentR != r || currentC != c && canBeDragged){
            jeu.tabChemins[caseDebutCourant.getId()-1].ajouter(jeu.tabChemins[caseDebutCourant.getId()-1].getTrajetSize(), jeu.grille.getCase(currentR, currentC));
            jeu.tabChemins[caseDebutCourant.getId()-1].removeDuplicate();
            canBeDragged = jeu.modifTrajet(caseDebutCourant, canBeDragged);
            jeu.appliquerChemin();
            
            currentR = r;
            currentC = c;
            
            /*if(jeu.grille.getCase(currentR, currentC).getId() == caseDebutCourant.getId() && jeu.grille.isSymbol(currentR, currentC)){
                caseDebutPassed++;
            }*/
        }
        setChanged();
        notifyObservers();
    }    

    public void stopDD(int c, int r) {
        if(currentR != r || currentC != c && canBeDragged){
            jeu.tabChemins[caseDebutCourant.getId()-1].ajouter(jeu.tabChemins[caseDebutCourant.getId()-1].getTrajetSize(), jeu.grille.getCase(currentR, currentC));
            jeu.tabChemins[caseDebutCourant.getId()-1].removeDuplicate();
            jeu.modifTrajet(caseDebutCourant, canBeDragged);
            jeu.appliquerChemin();
            currentR = r;
            currentC = c;
        }
        canBeDragged = true;
        caseDebutPassed = 0;
        setChanged();
        notifyObservers();
    }
    
    public void reinitGrille(){
        for(int i=0; i< jeu.tabChemins.length; i++){
            jeu.appliquerViderChemin(jeu.tabChemins[i]);
            jeu.tabChemins[i].viderChemin();
        }
        setChanged();
        notifyObservers();
    }
    
    public void modifierNiveau(int group, int level){
        jeu = new JeuLignes(group, level);
        setChanged();
        notifyObservers();
    }

}