/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import cases.Case;

/**
 *
 * @author Khaled
 */
public class Chemin {
    private Case caseDebut;
    private Case caseFin;
    
    public Chemin(Case _caseDebut, Case _caseFin){
        caseDebut = _caseDebut;
        caseFin = _caseFin;
    }
    
    public Case getCaseDebut(){
        return caseDebut;
    }
    
    public void setCaseDebut(Case _case){
        caseDebut = _case;
    }
    
    public Case getCaseFin(){
        return caseFin;
    }
    
    public void setCaseFin(Case _case){
        caseFin = _case;
    }
    
    public boolean chemainValide(Case case1, Case case2){
        return (( (case1.getId() == case2.getId()) || (case1.getId() == 0 || case2.getId() == 0) ) && case1 != case2);
    }
}
