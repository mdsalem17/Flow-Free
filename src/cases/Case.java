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
public class Case {
    
    private int id;
    private int x;
    private int y;

    public Case(int _id, int _x, int _y){
        this.id = _id;
        this.x = _x;
        this.y = _y;
    }
    
    public void setId(int _id){
        this.id = _id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setX(int _x){
        this.x = _x;
    }
    
    public int getX(){
        return this.x;
    }
    
    public void setY(int _y){
        this.y = _y;
    }
    
    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        if(object instanceof Case){
            Case case1 = (Case) object;
            return (this.x == case1.getX() && this.y == case1.getY());
        }
        return false;
    }
    
}
