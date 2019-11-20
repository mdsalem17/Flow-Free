package jeu;

import cases.Case;
import jeu.Chemin;
import jeu.Grille;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Salem
 */

public class JeuLignes {
    public Grille grille;
    private Chemin cheminCourant;
    private Chemin tabChemins[];
    
    public JeuLignes(){
        grille = new Grille();
        grille.init();
        cheminCourant = new Chemin(null, null);
        System.out.println("nb case symbole dans const = "+grille.getNbCaseSymbol());
        tabChemins = new Chemin[grille.getNbCaseSymbol()];
        initTabChemin();
    }
    
    private void initTabChemin(){
        for(int i = 0 ; i < grille.getNbCaseSymbol(); i++){
            tabChemins[i] = new Chemin(null, null);
        }
    }
    
    public void setTabChemin(int index, Case caseDebut, Case caseFin){
        tabChemins[index].setCaseDebut(caseDebut);
        tabChemins[index].setCaseFin(caseFin);
    }
    
    public boolean partieTerminee(){
        for(int i = 0; i < grille.getTaille(); i++){
            for(int j = 0; j < grille.getTaille(); j++){
             if(grille.getCase(i, j).getId() == 0)
                    return false;
            }
        }
        return true;
    }
    
}

