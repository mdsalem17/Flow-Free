/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.awt.Button;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Khaled
 */
public class VueControleur extends Application {
    
    Modele m;
    int grilleSize;
    
    @Override
    public void start(Stage stage) {

        // initialisation du modèle que l'on souhaite utiliser
        m = new Modele();
        grilleSize = m.jeu.grille.getTaille();
        
        // permet de placer les diffrents boutons dans une grille
        Circle[][] tabCircles = new Circle[grilleSize][grilleSize];
        Polyline[][] tabLines = new Polyline[grilleSize][grilleSize];
        Rectangle[][] tabRects = new Rectangle[grilleSize][grilleSize];
        Double[] cornerPoints = {0.0, 0.0, 22.0, 0.0, 22.0, 22.0};
        Double[] verticalPoints = {100.0, 0.0, 100.0, 42.0};
        Double[] horizontalPoints = {0.0, 100.0, 42.0, 100.0};

        stage.setTitle("Pro");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("game-grid");
        grid.setAlignment(Pos.CENTER);

        m.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                
                for (int column = 0; column < grilleSize; column++) {
                    for (int row = 0; row < grilleSize; row++) {

                        final int fColumn = column;
                        final int fRow = row;
                        final int fId = m.jeu.grille.getCase(fRow, fColumn).getId();
                        final int pos = m.jeu.grille.getCase(fRow, fColumn).getPosition();

                        Circle circle = new Circle(20, 20, 20);
                        Polyline line = new Polyline();
                        Rectangle rect = new Rectangle(0, 0, 58, 58);

                        if(fId > 0){
                            circle.setFill(m.couleur[Math.abs(fId)]);
                            line.getPoints().addAll(new Double[]{
                               5.0, 5.0,
                               5.0, 5.0
                            });
                            GridPane.setHalignment(line, HPos.CENTER);
                            circle.setFill(m.couleur[Math.abs(fId)]);
                        }else{
                            circle.setFill(m.couleur[0]);
                            /*System.err.println("m.currentR = "+m.currentR+", m.lastR = "+m.lastR+", m.currentC = "+m.currentC+", m.lastC = "+m.lastC);
                            if(m.lastR == fRow && m.lastC != fColumn){
                                line.getPoints().addAll(horizontalPoints);
                            }else if(m.currentR != fRow && m.currentC == fColumn){
                                line.getPoints().addAll(verticalPoints);
                            }*/
                            line.getPoints().addAll(horizontalPoints);
                            line.setStroke(m.couleur[Math.abs(fId)]);
                        }
                        if(m.jeu.grille.getCase(row,column).getCrossed())
                            rect.setFill(m.backgroundCouleur[Math.abs(fId)]);
                        switch (pos) {
                            case 1:
                                line.setRotate(0);
                                break;
                            case 2:
                                line.setRotate(-90);
                                break;
                            case 3:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setRotate(-90);
                                line.setStroke(m.couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.RIGHT);
                                GridPane.setValignment(line, VPos.BOTTOM);
                                break;
                            case 4:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setStroke(m.couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.LEFT);
                                GridPane.setValignment(line, VPos.BOTTOM);
                                break;
                            case 5:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setRotate(-180);
                                line.setStroke(m.couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.RIGHT);
                                GridPane.setValignment(line, VPos.TOP);
                                break;
                            case 6:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setRotate(90);
                                line.setStroke(m.couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.LEFT);
                                GridPane.setValignment(line, VPos.TOP);
                                break;
                            default:
                                break;
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

                        grid.setHalignment(circle, HPos.CENTER);
                        grid.setHalignment(rect, HPos.CENTER);
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
            ColumnConstraints column = new ColumnConstraints(60);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < m.jeu.grille.getTaille(); i++) {
            RowConstraints row = new RowConstraints(60);
            grid.getRowConstraints().add(row);
        }

        for (int column = 0; column < m.jeu.grille.getTaille(); column++) {
            for (int row = 0; row < m.jeu.grille.getTaille(); row++) {
                
                final int fColumn = column;
                final int fRow = row;
                final int fId = m.jeu.grille.getCase(row,column).getId();
                
                Circle circle = new Circle(20, 20, 20);
                circle.setFill(m.couleur[Math.abs(fId)]);
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
                grid.setHalignment(circle, HPos.CENTER);
                grid.add(tabCircles[column][row], column, row);
            }
        }

        /*HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(new Button("niveau Suivant"));
        grid.add(hbBtn, 1, 4);*/
        
        Scene scene = new Scene(grid, (m.jeu.grille.getTaille() * 60) + 100, (m.jeu.grille.getTaille() * 60) + 100, Color.BLACK);
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