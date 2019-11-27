/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Khaled
 */
public class VueControleur extends Application {
    
    Modele m;
    int lastCaseR, lastCaseC;
    
    @Override
    public void start(Stage stage) {

        // initialisation du modèle que l'on souhaite utiliser
        m = new Modele();
        lastCaseR = 0;
        lastCaseC = 0;
        
        // permet de placer les diffrents boutons dans une grille
        Circle[][] tabCircles = new Circle[m.jeu.grille.getTaille()][m.jeu.grille.getTaille()];
        Line[][] tabLines = new Line[m.jeu.grille.getTaille()][m.jeu.grille.getTaille()];
        Rectangle[][] tabRects = new Rectangle[m.jeu.grille.getTaille()][m.jeu.grille.getTaille()];

        stage.setTitle("Flow Free");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("game-grid");
        grid.setAlignment(Pos.CENTER);

        m.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                
                //Do things...

        for (int column = 0; column < m.jeu.grille.getTaille(); column++) {
            for (int row = 0; row < m.jeu.grille.getTaille(); row++) {
                
                final int fColumn = column;
                final int fRow = row;
                final int c = m.jeu.grille.getCase(fRow, fColumn).getId();
                final int pos = m.jeu.grille.getCase(fRow, fColumn).getPosition();
                
                Circle circle = new Circle();
                Line line = new Line();
                Rectangle rect = new Rectangle(0, 0, 38, 38);
                
                if(c > 0){
                    circle.setRadius(20.0f);
                    circle.setScaleX(0.8);
                    circle.setScaleY(0.8);
                    circle.setFill(m.couleur[Math.abs(c)]);
                    line.setStartX(0.0);
                    line.setStartY(0.0); 
                    line.setEndX(0.0); 
                    line.setEndY(0.0);
                    circle.setFill(m.couleur[Math.abs(c)]);
                }else{
                    circle.setRadius(20.0f);
                    circle.setScaleX(0.8);
                    circle.setScaleY(0.8);
                    circle.setFill(m.couleur[0]);
                    line.setStartX(0.0); 
                    line.setStartY(100.0); 
                    line.setEndX(25.0);
                    line.setEndY(100.0);
                    line.setStroke(m.couleur[Math.abs(c)]);
                }
                if(m.jeu.grille.getCase(row,column).getCrossed())
                    rect.setFill(m.backgroundCouleur[Math.abs(c)]);
                if(pos == 1){
                    line.setRotate(0);
                }else if(pos == 2){
                    line.setRotate(-90);
                }else if(pos == 3){
                    line.setRotate(-45);
                }else if(pos == 4){
                    //need to change column and row!!!!!!!!!!!!
                    tabLines[fColumn][fRow].setStartX(0.0); 
                    tabLines[fColumn][fRow].setStartY(100.0); 
                    tabLines[fColumn][fRow].setEndX(5.0);
                    tabLines[fColumn][fRow].setEndY(100.0);
                    line.setRotate(45);
                }
                line.setStrokeWidth(15);
                tabLines[column][row] = line; 
                tabCircles[column][row] = circle;
                tabRects[column][row] = rect;
                Pane pane = new Pane();
                
                pane.getStyleClass().add("game-grid-cell");
                if(column == 0){
                    pane.getStyleClass().add("first-column");
                }
                if(row == 0){
                    pane.getStyleClass().add("first-row");
                }
                    
                grid.add(pane, column, row);
                grid.add(tabRects[column][row], column, row);
                grid.add(tabLines[column][row], column, row);
                grid.add(tabCircles[column][row], column, row);
                
                circle.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {

                        //Detecter si le drag commence d'une case Symbole ou pas
                        if(m.jeu.grille.isSymbol(fRow, fColumn)){
                            Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();       
                            content.putString(""); // non utilisé actuellement
                            db.setContent(content);
                            event.consume();
                            m.startDD(fColumn, fRow);
                        }
                    }
                });

                circle.setOnDragEntered(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        
                        m.parcoursDD(fColumn, fRow);
                        event.consume();
                    }
                });
                
                circle.setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        
                        // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                        m.stopDD(fColumn, fRow);
                    }
                });
                
            }
        }

            }
        });
        
        for(int i = 0; i < m.jeu.grille.getTaille(); i++) {
            ColumnConstraints column = new ColumnConstraints(40);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < m.jeu.grille.getTaille(); i++) {
            RowConstraints row = new RowConstraints(40);
            grid.getRowConstraints().add(row);
        }

        for (int column = 0; column < m.jeu.grille.getTaille(); column++) {
            for (int row = 0; row < m.jeu.grille.getTaille(); row++) {
                
                final int fColumn = column;
                final int fRow = row;
                final int c = m.jeu.grille.getCase(row,column).getId();
                
                Circle circle = new Circle();
                if(m.jeu.grille.isChemin(row,column)){
                    circle.setRadius(20.0f);
                    circle.setScaleX(0.4);
                    circle.setScaleY(2);
                }else{
                    circle.setRadius(20.0f);
                    circle.setScaleX(0.8);
                    circle.setScaleY(0.8);
                }
                circle.setFill(m.couleur[Math.abs(c)]);
                tabCircles[column][row] = circle;
                Pane pane = new Pane();
                
                pane.getStyleClass().add("game-grid-cell");
                if (column == 0) {
                    pane.getStyleClass().add("first-column");
                }
                if (row == 0) {
                    pane.getStyleClass().add("first-row");
                }
                grid.add(pane, column, row);
                
                circle.setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {

                        //Detecter si le drag commence d'une case Symbole ou pas
                        if(m.jeu.grille.isSymbol(fRow, fColumn)){
                            Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();       
                            content.putString(""); // non utilisé actuellement
                            db.setContent(content);
                            event.consume();
                            m.startDD(fColumn, fRow);
                        }
                    }
                });

                circle.setOnDragEntered(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        
                        m.parcoursDD(fColumn, fRow);
                        event.consume();
                    }
                });
                
                circle.setOnDragDone(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        
                        // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                        m.stopDD(fColumn, fRow);
                        
                    }
                });
                grid.add(tabCircles[column][row], column, row);
            }
        }

        Scene scene = new Scene(grid, (m.jeu.grille.getTaille() * 40) + 100, (m.jeu.grille.getTaille() * 40) + 100, Color.BLACK);
        scene.getStylesheets().add("mvc/game.css");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
