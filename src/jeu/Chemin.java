/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import cases.Case;
import cases.CaseChemin;
import cases.CaseSymbol;
import java.util.ArrayList;

/**
 *
 * @author Khaled
 */
public class Chemin {
    private ArrayList<Case> trajet;

    
    public Chemin(){
        trajet = new ArrayList<Case>() {};
    }
    
    public Chemin(CaseSymbol _caseDebut){
        trajet = new ArrayList<Case>();
    }
    
    public Case getChemineFin(){
        return trajet.get(trajet.size()-1);
    }
    
    public boolean ajouter(Case _case, int id){
        if( _case.getId()==0 || _case.getId()==id){
            trajet.add(_case);
            return true;
        } else return false;        
    }
    
}
