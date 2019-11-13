/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cases;

/**
 *
 * @author Khaled
 */
public abstract class Case {
    
    private int id;

    public Case(int _id){
        this.id = _id;
    }
    
    public boolean isSymbol(Case c){
        return (c instanceof CaseSymbol);
    }
    
    public boolean isChemin(Case c){
        return (c instanceof CaseChemin);
    }
    
    public void setId(int _id){
        this.id = _id;
    }
    
    public int getId(){
        return this.id;
    }
}
