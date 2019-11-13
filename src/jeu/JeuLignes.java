package jeu;

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
    int nb;
    Grille plateau[][];
    Chemin currentPath;
    
    public JeuLignes(){
        plateau = new Grille[nb][nb];
    }
    public void jouerPartie(){
        
    }
    
    public boolean endGamePlay(){
        return true;
    }
    
}

