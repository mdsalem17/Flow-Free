/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import cases.Case;
import cases.CaseSymbol;
import cases.CaseChemin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Khaled
 */
public class Grille {
    
    private Case plateau[][];
    private int n;
    private int nbCaseSymbol;
    
    public Grille(int _n){
        n = _n;
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
        if(_id != 0){
            plateau[x][y].setCrossed(true);
        }else{
            plateau[x][y].setCrossed(false);
        }
        plateau[x][y].setId(_id);
    }
    
    public void init(int level){   
        readGridFromFile(n, level);
        for(int i=0; i<plateau.length; i++){
            for(int j=0; j<plateau.length; j++){
                if(plateau[i][j].getId()==0) {
                    plateau[i][j] = new CaseChemin(plateau[i][j].getId(), i, j);
                } else {
                    plateau[i][j] = new CaseSymbol(plateau[i][j].getId(), i, j);
                    nbCaseSymbol++;
                }
            }
        }
        nbCaseSymbol = nbCaseSymbol/2;
    }
    
    private void readGridFromFile(int gridSize, int gridNumber){
        FileReader inputFile;
        int currentId = 0;
        
        try {
            inputFile = new FileReader(".\\ressources\\level_" + gridSize + "x" + gridSize
                    +"\\" + gridNumber + ".txt") ;
            Scanner sc = new Scanner(new BufferedReader(inputFile));
            while (sc.hasNextLine()) {
                for (int i = 0; i < gridSize; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < gridSize; j++) {
                        currentId = Integer.parseInt(line[j]);
                        plateau[i][j] = new Case(currentId, i, j) ;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void saveGridToFile (String filename) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < plateau.length; i++) {
            for(int j =0; j < plateau.length; j++) {
                outputWriter.write(plateau[i][j].getId() + " ");
            }
            outputWriter.write( '\n');
        }
        outputWriter.flush();
        outputWriter.close();
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
