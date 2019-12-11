/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import cases.Case;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Khaled
 */
public class Chemin {
    protected ArrayList<Case> trajet;
    
    public Chemin(){
        trajet = new ArrayList<Case>() {};
    }
    
    public void removeDuplicate(){
        java.util.List<Case> list = trajet.stream().distinct().collect(Collectors.toList());
        trajet = (ArrayList<Case>) list;
    }
    
    public int getTrajetSize(){
        return trajet.size();
    }
    
    public Case getChemineFin(){
        return trajet.get(trajet.size()-1);
    }
    
    public void ajouter(int indice, Case _case){
        if(trajet.isEmpty() || Math.abs(_case.getId()) == trajet.get(0).getId()){
            trajet.add(indice, _case);
        }
    }
    
    public boolean contientCase(Case case1){
        for(int i = 0; i < trajet.size()-1; i++){
            if(trajet.get(i).equals(case1))
                return true;
        }
        return false;
    }
    
    public void ajustTrajet(int indice){
        for(int i = indice; i <= trajet.size()-1; i++){
            trajet.remove(i);
        }
        if(trajet.size() == 1 || trajet.get(0).getId() > 0){
            viderChemin();
        }
    }
    
    public void viderChemin(){
        trajet.clear();
    }
        
    public Case getCaseTrajet(int i){
        if(i < trajet.size()){
            return trajet.get(i);
        }else throw new IndexOutOfBoundsException("Error: IndexOutOfBoundsException "+trajet.get(i).getX()+", "+trajet.get(i).getY());
    }
    
    public void saveCheminToFile (String filename, boolean b) throws IOException{
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename, b));
        for (int i = 0; i < trajet.size(); i++) {
            outputWriter.write( getCaseTrajet(i).getId() + ","
                                + getCaseTrajet(i).getX() + ","
                                + getCaseTrajet(i).getY() + ";");
        }
        outputWriter.write( '\n');

        outputWriter.flush();
        outputWriter.close();
    }
    
    public int getCheminShape(int indice){
        if(indice > 0 && indice < trajet.size()){
            
            int x1 = trajet.get(indice-1).getX();
            int y1= trajet.get(indice-1).getY();
            
            int x2 = trajet.get(indice).getX();
            int y2 = trajet.get(indice).getY();

            if(trajet.size() - indice > 1){
                int x3 = trajet.get(indice+1).getX();
                int y3 = trajet.get(indice+1).getY();

                if (x3 == x1) {
                    return 1;
                }

                else if (y3 == y1) {
                    return 2;
                }

                else if (x3 > x1) {
                    if (y3 < y1) {
                        if (x3 == x2)
                            return 6;
                        else
                            return 3;
                    }
                    else {
                        if (x3 == x2)
                            return 5;
                        else
                            return 4;
                    }

                }
                else {
                    if (y3 < y1) {
                        if (x3 == x2)
                            return 4;
                        else
                            return 5;
                    }
                    else {
                        if (x3 == x2)
                            return 3;
                        else
                            return 6;
                    }
                }
            }else if(trajet.size() - indice == 1){            
                if(x1 == x2 && y1 != y2)
                    return 1;
                else if(x1 != x2 && y1 == y2)
                    return 2;
            }
            
        }else if(indice == 0 && trajet.size() > 1){
            int x1 = trajet.get(indice).getX();
            int y1= trajet.get(indice).getY();
            
            int x2 = trajet.get(indice+1).getX();
            int y2 = trajet.get(indice+1).getY();
            
            if(x1 == x2 && y1 != y2)
                    return 1;
                else if(x1 != x2 && y1 == y2)
                    return 2;
        }

        return 0;
    }
    
}