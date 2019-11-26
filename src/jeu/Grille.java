/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import cases.Case;
import cases.CaseSymbol;
import cases.CaseChemin;

/**
 *
 * @author Khaled
 */
public class Grille {
    
    private Case plateau[][];
    private int n;
    private int nbCaseSymbol;
    
    public Grille(){
        n = 3;
        nbCaseSymbol = 0;
        plateau = new Case[n][n];
    }
    
    public int getTaille(){
        return n;
    }
    
    public int getNbCaseSymbol(){
        return nbCaseSymbol;
    }
    
    public Case getCase(int x, int y){
        return plateau[x][y];
    }
    
    public boolean isSymbol(int x, int y){
        return (plateau[x][y] instanceof CaseSymbol);
    }
    
    public boolean isChemin(int x, int y){
        return (plateau[x][y] instanceof CaseChemin);
    }
    
    public void setCaseId(int _id, int x, int y){
        plateau[x][y].setId(_id);
    }
    
    public void init(){
        initCaseSymbol();
        
        for(int i = 0; i < n; i++){
            for(int j = 0 ; j < n; j++){
                if(!(plateau[i][j] instanceof CaseSymbol))
                    plateau[i][j] = new CaseChemin(0, i ,j) {};
                else
                    nbCaseSymbol++;
            }
        }
        nbCaseSymbol = nbCaseSymbol/2;        
    }
    
    private void initCaseSymbol(){
        plateau[0][0] = new CaseSymbol(1, 0, 0) {};
        plateau[2][1] = new CaseSymbol(1, 2, 1) {};
        
        plateau[0][2] = new CaseSymbol(2, 0, 2) {};
        plateau[n-1][n-1] = new CaseSymbol(2, n-1, n-1) {};
    }

    @Override
    public String toString(){
        String text = "";
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                text += " " + plateau[i][j].getId();
            }
            text += "\n";
        }
        return text;
    }
    
}
