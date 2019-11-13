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
    
    public Grille(){
        n = 3;
        plateau = new Case[n][n];
    }
    
    public int getTaille(){
        return n;
    }
    
    public int getCase(int x, int y){
        return plateau[x][y].getId();
    }
    
    public void init(){
        for(int i = 0; i < n; i++){
            for(int j = 0 ; j < n; j++){
                plateau[i][j] = new Case(0) {};
            }
        }
        plateau[0][0].setId(1);
        plateau[2][1].setId(1);
        
        plateau[0][2].setId(2);
        plateau[n-1][n-1].setId(2);
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
