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
    
    public JeuLignes(){
        grille = new Grille();
        grille.init();
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
                if(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getId()) != Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i+1).getId())){
                    tabChemins[caseCourant.getId()-1].ajustTrajet(i+1);
                }
                //horizontal case missing
                else if(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getX()-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) > 1 && (tabChemins[caseCourant.getId()-1].trajet.get(i).getY() == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY())){
                    for(int k = 0 ; k < Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getX()-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()); k++){
                        if(tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX() < tabChemins[caseCourant.getId()-1].trajet.get(i+k+1).getX()){
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i+k).getId())), tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX()+1, tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY()));
                        }else{
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i+k).getId())), tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX()-1, tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY()));
                        }
                    }
                }
                //vertical case missing
                else if((tabChemins[caseCourant.getId()-1].trajet.get(i).getX() == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) && Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getY()-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()) > 1){
                    for(int k = 0 ; k < Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getY()-tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()); k++){

                        if(tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY()< tabChemins[caseCourant.getId()-1].trajet.get(i+k+1).getY()){
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i+k).getId())), tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX(), tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY()+1));
                        }else{
                            tabChemins[caseCourant.getId()-1].ajouter(i+k+1, new Case(-(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i+k).getId())), tabChemins[caseCourant.getId()-1].trajet.get(i+k).getX(), tabChemins[caseCourant.getId()-1].trajet.get(i+k).getY()-1));
                        }
                    }
                }
                //corners
                else if(((tabChemins[caseCourant.getId()-1].trajet.get(i).getX() == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()+1) || (tabChemins[caseCourant.getId()-1].trajet.get(i).getX()+1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()))
                            && ((tabChemins[caseCourant.getId()-1].trajet.get(i).getY() == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()+1) || (tabChemins[caseCourant.getId()-1].trajet.get(i).getY()+1 == tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()))){
                    appliquerViderChemin(tabChemins[caseCourant.getId()-1]);
                    appliquerChemin();
                    return false;
                }
                //plusieurs cases manquantes
                /*else if(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getX() - tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) == 1 && Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getY() - tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()) > 1){
                    return false;
                }
                else if(Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getX() - tabChemins[caseCourant.getId()-1].trajet.get(i+1).getX()) > 1 && Math.abs(tabChemins[caseCourant.getId()-1].trajet.get(i).getY() - tabChemins[caseCourant.getId()-1].trajet.get(i+1).getY()) == 1){
                    return false;
                }*/
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
        chemin.viderChemin();
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
        return true;
    }
    
}

