/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu;

import cases.Case;
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
            System.out.println(" =>>>>>>> case "+i+" removed from trajet, x et  => "+trajet.get(i).getX()+", "+trajet.get(i).getY());
            trajet.remove(i);
        }
    }
    
    public void viderChemin(){
        trajet.clear();
    }
        
    public Case getCaseTrajet(int i){
        if(i < trajet.size()){
            return trajet.get(i);
        }else throw new IndexOutOfBoundsException("Error: IndexOutOfBoundsException");
    }
    
    public int getCheminShape(int indice){
        if(indice > 0 && indice < trajet.size()){
            int x1 = trajet.get(indice-1).getX();
            int y1= trajet.get(indice-1).getY();
            
            int x2 = trajet.get(indice).getX();
            int y2 = trajet.get(indice).getY();
            
            //possible corer
            if(trajet.size() - indice > 1){
                int x3 = trajet.get(indice+1).getX();
                int y3 = trajet.get(indice+1).getY();
                
                //upper left corner
                if((x1 == (x3-1) && y1 == (y3+1) || x1 == (x3+1) && y1 == (y3-1)) && (x1 == x2 || x2 == x3) && (y1 == y2 || y2 == y3))
                    return 3;
                //upper rigt corner
                else if((x1 == (x3-1) && y1 == (y3-1) || x1 == (x3+1) && y1 == (y3+1)) && (x1 == x2 || x2 == x3) && (y1 == y2 || y2 == y3))
                    return 4;
            }
            
            if(x1 == x2 && y1 != y2)
                return 1;
            else if(x1 != x2 && y1 == y2)
                return 2;
        }
        return 0;
    }
    
}
