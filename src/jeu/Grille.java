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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Khaled
 */
public class Grille {
    
    private Case plateau[][];
    private int n;
    private int nbCaseSymbol;
    
    public Grille(){
        n = 4;
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
        int[][] grid = readGridFromFile(n,level);
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid.length; j++){
                if(grid[i][j]==0) {
                    plateau[i][j] = new CaseChemin(grid[i][j], i, j);
                } else {
                    plateau[i][j] = new CaseSymbol(grid[i][j], i, j);
                    nbCaseSymbol++;
                }
            }
        }
        nbCaseSymbol = nbCaseSymbol/2;
        System.out.println(Arrays.deepToString(grid));
    }
    
    private void initCaseSymbol(){
        plateau[0][0] = new CaseSymbol(1, 0, 0) {};
        plateau[2][1] = new CaseSymbol(1, 2, 1) {};
        
        plateau[0][2] = new CaseSymbol(2, 0, 2) {};
        plateau[n-1][n-1] = new CaseSymbol(2, n-1, n-1) {};
    }
    
    public static int[][] readGridFromFile(int gridSize, int gridNumber){
        int[][] grid = new int[gridSize][gridSize];
        FileReader inputFile;

        try {
            inputFile = new FileReader(".\\ressources\\level_" + gridSize + "x" + gridSize
                    +"\\" + gridNumber + ".txt") ;
            Scanner sc = new Scanner(new BufferedReader(inputFile));
            while (sc.hasNextLine()) {
                for (int i = 0; i < gridSize; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < gridSize; j++) {
                        grid[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grid;
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
