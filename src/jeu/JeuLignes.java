package jeu;

import cases.Case;
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
    public Chemin tabChemins[];
    
    public JeuLignes(int level){
        grille = new Grille();
        grille.init(level);
        System.out.println(grille.toString());
        System.out.println(grille.getNbCaseSymbol());
        tabChemins = new Chemin[grille.getNbCaseSymbol()];
        initTabChemin();
    }
    
    private void initTabChemin(){
        for(int i = 0 ; i < grille.getNbCaseSymbol(); i++){
            tabChemins[i] = new Chemin();
        }
    }
    
    public boolean modifTrajet(Case caseCourant, boolean draggable){
        if(draggable){
            
            for(int i = 0; i < tabChemins[caseCourant.getId()-1].trajet.size()-1; i++){
                
                int x1 = tabChemins[caseCourant.getId()-1].trajet.get(i).getX();
                int y1 = tabChemins[caseCourant.getId()-1].trajet.get(i).getY();
                
                if(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getId()) !=
                        Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i+1).getId())){
                    tabChemins[caseCourant.getId()-1].ajustTrajet(i);
                    appliquerViderChemin(tabChemins[caseCourant.getId()-1]);
                    return false;
                    
                }
                //horizontal case missing
                else if(Math.abs(x1-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) > 1 &&
                        (y1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY())){
                    for(int k = 0 ; k < Math.abs(x1-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()); k++){
                        
                        int x_k = tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX();
                        int y_k = tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY();
                        int id_k = tabChemins[caseCourant.getId()-1].trajet.get(i+k).getId();
                        
                        if(x_k < tabChemins[caseCourant.getId()-1].trajet.get(i+k+1).getX()){
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(id_k)), x_k+1, y_k));
                        }else{
                            tabChemins  [caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(id_k)), x_k-1, y_k));
                        }
                    }
                }
                //vertical case missing
                else if((x1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) && Math.abs(y1-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()) > 1){
                    for(int k = 0 ; k < Math.abs(y1-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()); k++){

                        int x_k = tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX();
                        int y_k = tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY();
                        int id_k = tabChemins[caseCourant.getId()-1].trajet.get(i+k).getId();
                        
                        if(y_k< tabChemins[caseCourant.getId()-1].trajet.get(i+k+1).getY()){
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(id_k)), x_k, y_k+1));
                        }else{
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(id_k)), x_k, y_k-1));
                        }
                    }
                }
                //corners
                else if(((x1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()+1) ||
                        (x1+1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX())) &&
                        ((y1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()+1) ||
                        (y1+1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()))){
                    appliquerViderChemin(tabChemins[caseCourant.getId()-1]);
                    tabChemins[caseCourant.getId()-1].viderChemin();
                    appliquerChemin();
                    return false;
                }
                else if(Math.abs(x1 - tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) > 1 ||
                        Math.abs(y1 - tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()) > 1){
                    appliquerViderChemin(tabChemins[caseCourant.getId()-1]);
                    tabChemins[caseCourant.getId()-1].ajustTrajet(i);
                    return false;
                }
            }
        }
        return true;
    }
    
    public void appliquerViderChemin(Chemin chemin){
        int x, y;
        for(int i = 0 ; i < chemin.getTrajetSize(); i++){
            x = chemin.getCaseTrajet(i).getX();
            y = chemin.getCaseTrajet(i).getY();
            if(grille.isChemin(x, y)){
                grille.setCaseId(0, x, y);
            }
            grille.getCase(x, y).setCrossed(false);
        }
    }
    
    public void appliquerChemin(){
        int _id;        
        for(int k = 0; k < tabChemins.length; k++){
            for(int i = 0; i < tabChemins[k].getTrajetSize(); i++){
                _id = grille.getCase(tabChemins[k].getCaseTrajet(i).getX(), tabChemins[k].getCaseTrajet(i).getY()).getId();
                if(_id < 0 && _id != tabChemins[k].getCaseTrajet(i).getId()){
                    tabChemins[k].ajustTrajet(i+1);
                }
                grille.setCaseId(tabChemins[k].getCaseTrajet(i).getId(), tabChemins[k].getCaseTrajet(i).getX(), tabChemins[k].getCaseTrajet(i).getY());
                grille.getCase(tabChemins[k].getCaseTrajet(i).getX(), tabChemins[k].getCaseTrajet(i).getY())
                        .setPosition(tabChemins[k].getCheminShape(i));
            }
        }
    }
    
    public boolean partieTerminee(){
        for(int i = 0; i < grille.getTaille(); i++){
            for(int j = 0; j < grille.getTaille(); j++){
             if(grille.getCase(i, j).getId() == 0)
                    return false;
            }
        }
        for(int k = 0; k < tabChemins.length; k++){
            if(tabChemins[k].getCaseTrajet(0).getId() != tabChemins[k].getCaseTrajet(tabChemins[k].getTrajetSize()-1).getId())
                return false;
        }
        return true;
    }
    
    public String selectLevel(int size){
        switch (size) {
            case 3:
                return "level_3x3";
            case 4:
                return "level_4x4";
            case 5:
                return "level_5x5";
            default:
                return "no such level";
        }
    }
    
}

