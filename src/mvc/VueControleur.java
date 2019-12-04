
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Khaled
 */
public class VueControleur extends Application {

    Modele m;
    Scene scene, scene2;
    GridPane grid;
    int currentLevel, currentGroup;
    BorderPane border;
    Color couleur[], backgroundCouleur[];
    
    public BorderPane Jeu(int group, int level, Stage stage){
        
        couleur = new Color[6];
        couleur[0] = Color.TRANSPARENT;
        couleur[1] = Color.BLUE;
        couleur[2] = Color.RED;
        couleur[3] = new Color(0, 0.90, 0.23, 1);
        couleur[4] = new Color(1, 0.5, 0, 1);
        couleur[5] = new Color(0.68, 0.12, 0.68, 1);
        
        backgroundCouleur = new Color[6];
        backgroundCouleur[0] = Color.TRANSPARENT;
        backgroundCouleur[1] = new Color(0, 0, 0.4, 1);
        backgroundCouleur[2] = new Color(0.4, 0, 0, 0.5);
        backgroundCouleur[3] = new Color(0, 0.90, 0.23, 0.3);
        backgroundCouleur[4] = new Color(1, 0.5, 0, 0.3);
        backgroundCouleur[5] = new Color(0.68, 0.12, 0.68, 0.3);
        
        // initialisation du modèle que l'on souhaite utiliser
        m = new Modele(group, level);

        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane grid au centre)
        border = new BorderPane();
        
        grid = new GridPane();
        
        // permet de placer les diffrents boutons dans une grille
        Circle[][] tabCircles = new Circle[m.jeu.grille.getTaille()][m.jeu.grille.getTaille()];
        Polyline[][] tabLines = new Polyline[m.jeu.grille.getTaille()][m.jeu.grille.getTaille()];
        Rectangle[][] tabRects = new Rectangle[m.jeu.grille.getTaille()][m.jeu.grille.getTaille()];
        Double[] cornerPoints = {0.0, 0.0, 22.0, 0.0, 22.0, 22.0};
        Double[] verticalPoints = {100.0, 0.0, 100.0, 42.0};
        Double[] horizontalPoints = {0.0, 100.0, 42.0, 100.0};

        grid.getStyleClass().add("game-grid");
        grid.setAlignment(Pos.CENTER);
        
        HBox hboxTop = new HBox();
        hboxTop.setPadding(new Insets(30, 12, 0, 12));
        hboxTop.setSpacing(10);
        hboxTop.setAlignment(Pos.CENTER);
        hboxTop.setStyle("-fx-background-color: #000000;");

        Button btnSave = new Button("Save");
        btnSave.setTooltip(new Tooltip("Sauvegarder niveau"));
        btnSave.setPrefSize(100, 20);
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ButtonType btnAlertContinuer = new ButtonType("Continuer partie", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType btnAlertMenu = new ButtonType("Revenir au menu", ButtonBar.ButtonData.OK_DONE);
                Alert alert;
                try {
                    m.jeu.saveGameToFile();
                    
                } catch (IOException ex) {
                    Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
                alert = new Alert(AlertType.INFORMATION, "Partie enregistrée\nVeuillez cliquer sur « Continuer partie » pour continuer",
                        btnAlertContinuer, btnAlertMenu);
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == btnAlertMenu){
                    stage.setScene(scene2);
                }
            }
        });
        
        Text levelText = new Text("Niveau "+currentLevel);
        levelText.setFont(Font.font("Verdana", 20));
        levelText.setTextAlignment(TextAlignment.CENTER);
        levelText.setFill(Color.WHITE);
        
        Button btnChangerGroup = new Button(currentGroup+" x "+currentGroup);
        btnChangerGroup.setTooltip(new Tooltip("Changer niveau"));
        btnChangerGroup.setPrefSize(100, 20);
        btnChangerGroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(scene2);
            }
        });
        
        HBox hboxBottom = new HBox();
        hboxBottom.setPadding(new Insets(15, 12, 15, 12));
        hboxBottom.setSpacing(10);
        hboxBottom.setStyle("-fx-background-color: #000000;");

        Button btnNiveauPrec = new Button("🡸");
        btnNiveauPrec.setDisable((currentLevel == 1));
        Button btnNiveauSuiv = new Button("🡺");
        btnNiveauPrec.setTooltip(new Tooltip("Niveau précédent"));
        btnNiveauPrec.setPrefSize(100, 20);
        btnNiveauPrec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currentLevel > 1){
                    currentLevel--;
                    m.modifierNiveau(currentGroup, currentLevel);
                    btnNiveauSuiv.setDisable(false);
                }
                if(currentLevel == 1){
                    btnNiveauPrec.setDisable(true);
                }
            }
        });
        
        Button btnReinitialiser = new Button("🔄");
        btnReinitialiser.setTooltip(new Tooltip("Réinitialiser niveau"));
        btnReinitialiser.setPrefSize(100, 20);
        btnReinitialiser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                m.reinitGrille();
            }
        });
        
        btnNiveauSuiv.setTooltip(new Tooltip("Niveau suivant"));
        btnNiveauSuiv.setPrefSize(100, 20);
        btnNiveauSuiv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currentLevel < 4){
                    currentLevel++;
                    m.modifierNiveau(currentGroup, currentLevel);
                    btnNiveauPrec.setDisable(false);
                }
                if(currentLevel == 4){
                    btnNiveauSuiv.setDisable(true);
                }
            }
        });
        hboxTop.getChildren().addAll(btnSave, levelText, btnChangerGroup);
        hboxBottom.getChildren().addAll(btnNiveauPrec, btnReinitialiser, btnNiveauSuiv);
        hboxTop.setAlignment(Pos.CENTER);
        hboxBottom.setAlignment(Pos.CENTER);
        
        border.setTop(hboxTop);
        border.setBottom(hboxBottom);

        m.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                
                levelText.setText("Niveau "+currentLevel);
                btnChangerGroup.setText(currentGroup+" x "+currentGroup);
                                
                for (int column = 0; column < m.jeu.grille.getTaille(); column++) {
                    for (int row = 0; row < m.jeu.grille.getTaille(); row++) {

                        final int fColumn = column;
                        final int fRow = row;
                        final int fId = m.jeu.grille.getCase(fRow, fColumn).getId();
                        final int pos = m.jeu.grille.getCase(fRow, fColumn).getPosition();

                        Circle circle = new Circle(20, 20, 20);
                        Polyline line = new Polyline();
                        Rectangle rect = new Rectangle(0, 0, 58, 58);

                        if(fId > 0){
                            circle.setFill(couleur[Math.abs(fId)]);
                            line.getPoints().addAll(new Double[]{
                               5.0, 5.0,
                               5.0, 5.0
                            });
                            GridPane.setHalignment(line, HPos.CENTER);
                            circle.setFill(couleur[Math.abs(fId)]);
                        }else{
                            circle.setFill(couleur[0]);
                            /*System.err.println("m.currentR = "+m.currentR+", m.lastR = "+m.lastR+", m.currentC = "+m.currentC+", m.lastC = "+m.lastC);
                            if(m.lastR == fRow && m.lastC != fColumn){
                                line.getPoints().addAll(horizontalPoints);
                            }else if(m.currentR != fRow && m.currentC == fColumn){
                                line.getPoints().addAll(verticalPoints);
                            }*/
                            line.getPoints().addAll(horizontalPoints);
                            GridPane.setHalignment(line, HPos.CENTER);
                            GridPane.setValignment(line, VPos.CENTER);
                            line.setStroke(couleur[Math.abs(fId)]);
                        }
                        if(m.jeu.grille.getCase(row,column).getCrossed())
                            rect.setFill(backgroundCouleur[Math.abs(fId)]);
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
                                line.setStroke(couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.RIGHT);
                                GridPane.setValignment(line, VPos.BOTTOM);
                                break;
                            case 4:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setStroke(couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.LEFT);
                                GridPane.setValignment(line, VPos.BOTTOM);
                                break;
                            case 5:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setRotate(-180);
                                line.setStroke(couleur[Math.abs(fId)]);
                                GridPane.setHalignment(line, HPos.RIGHT);
                                GridPane.setValignment(line, VPos.TOP);
                                break;
                            case 6:
                                line = new Polyline();
                                line.getPoints().addAll(cornerPoints);
                                line.setRotate(90);
                                line.setStroke(couleur[Math.abs(fId)]);
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

                        GridPane.setHalignment(circle, HPos.CENTER);
                        GridPane.setHalignment(rect, HPos.CENTER);
                        grid.add(pane, column, row);
                        grid.add(tabRects[column][row], column, row);
                        grid.add(tabLines[column][row], column, row);
                        grid.add(tabCircles[column][row], column, row);

                        circle.setOnDragDetected(new EventHandler<MouseEvent>() {
                            @Override
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
                            @Override
                            public void handle(DragEvent event) {

                                m.parcoursDD(fColumn, fRow);
                                event.consume();
                            }
                        });

                        circle.setOnDragDone(new EventHandler<DragEvent>() {
                            @Override
                            public void handle(DragEvent event) {

                                // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                                m.stopDD(fColumn, fRow);
                                if(m.jeu.partieTerminee()){

                                    ButtonType btnAlertReinitialiser = new ButtonType("Réinitialiser", ButtonBar.ButtonData.OK_DONE);
                                    ButtonType btnAlertNiveauSuiv = new ButtonType("Niveau suivant", ButtonBar.ButtonData.CANCEL_CLOSE);
                                    ButtonType btnAlertMenu = new ButtonType("Revenir au menu", ButtonBar.ButtonData.CANCEL_CLOSE);
                                    Alert alert;

                                    if(currentLevel >= 4){
                                        alert = new Alert(AlertType.INFORMATION, "Bravo vous avez terminé la partie 🎉🎊\nVeuillez cliquer sur « Revenir au menu » pour continuer",
                                            btnAlertReinitialiser, btnAlertMenu);
                                    }else{
                                        alert = new Alert(AlertType.INFORMATION, "Bravo vous avez terminé la partie 🎉🎊\nVeuillez cliquer sur « Niveau suivant » pour continuer",
                                            btnAlertReinitialiser, btnAlertNiveauSuiv);
                                    }
                                    alert.setTitle("Partie gagnée!");
                                    alert.setHeaderText(null);
                                    Optional<ButtonType> result = alert.showAndWait();                                        
                                    
                                    if(result.get() == btnAlertReinitialiser) {
                                        m.reinitGrille();
                                    }else if(result.get() == btnAlertNiveauSuiv){
                                        currentLevel++;
                                        btnNiveauPrec.setDisable(false);
                                        btnNiveauSuiv.setDisable((currentLevel == 4)); 
                                        m.modifierNiveau(currentGroup, currentLevel);
                                    }else if(result.get() == btnAlertMenu){
                                        stage.setScene(scene2);
                                    }
                                }
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
                circle.setFill(couleur[Math.abs(fId)]);
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
                    @Override
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
                    @Override
                    public void handle(DragEvent event) {
                        
                        m.parcoursDD(fColumn, fRow);
                        event.consume();
                    }
                });
                
                circle.setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        
                        // attention, le setOnDragDone est déclenché par la source du Drag&Drop
                        m.stopDD(fColumn, fRow);
                        
                    }
                });
                GridPane.setHalignment(circle, HPos.CENTER);
                grid.add(tabCircles[column][row], column, row);
            }
        }
        
        border.setCenter(grid);
        return border;
    }
    
    @Override
    public void start(Stage stage) {
        
        //Scene 1
        Button group3 = new Button("Grille 3 x 3");
        group3.setStyle("-fx-font-size:23");
        group3.setPrefSize(150, 30);
        group3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentGroup = 3;
                currentLevel = 1;
                scene = new Scene(Jeu(currentGroup, currentLevel, stage), 450, 500, Color.BLACK);
                scene.getStylesheets().add("mvc/game.css");
                stage.setScene(scene);
            }
        });
        
        Button group4= new Button("Grille 4 x 4");
        group4.setStyle("-fx-font-size:23");
        group4.setPrefSize(150, 30);
        group4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentGroup = 4;
                currentLevel = 1;
                scene = new Scene(Jeu(currentGroup, currentLevel, stage), 450, 500, Color.BLACK);
                scene.getStylesheets().add("mvc/game.css");
                stage.setScene(scene);
            }
        });
        Button group5 = new Button("Grille 5 x 5");
        group5.setStyle("-fx-font-size:23");
        group5.setPrefSize(150, 30);
        group5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentGroup = 5;
                currentLevel = 1;
                scene = new Scene(Jeu(currentGroup, currentLevel, stage), 450, 500, Color.BLACK);
                scene.getStylesheets().add("mvc/game.css");
                stage.setScene(scene);
            }
        });
        
        HBox hBoxTopPane2 = new HBox();
        hBoxTopPane2.setPadding(new Insets(40, 12, 0, 12));
        hBoxTopPane2.setSpacing(10);
        hBoxTopPane2.setAlignment(Pos.CENTER);
        hBoxTopPane2.setStyle("-fx-background-color: #000000;");

        Text textTitre = new Text("Casse-tête - Lignes");
        textTitre.setFont(Font.font("Verdana", 40));
        textTitre.setTextAlignment(TextAlignment.CENTER);
        textTitre.setFill(Color.WHITE);
        
        hBoxTopPane2.getChildren().addAll(textTitre);
        
        VBox vBoxCenterPane2 = new VBox();
        vBoxCenterPane2.setPadding(new Insets(30, 12, 0, 12));
        vBoxCenterPane2.setSpacing(10);
        vBoxCenterPane2.setAlignment(Pos.CENTER);
        vBoxCenterPane2.setStyle("-fx-background-color: #000000;");        
        vBoxCenterPane2.getChildren().addAll(group3, group4, group5);
        
        VBox vBoxBottomPane2 = new VBox();
        vBoxBottomPane2.setPadding(new Insets(0, 12, 20, 12));
        vBoxBottomPane2.setSpacing(10);
        vBoxBottomPane2.setAlignment(Pos.CENTER);
        vBoxBottomPane2.setStyle("-fx-background-color: #000000;");

        Text textCredits = new Text("Khaled ABDRABO 11713323\n Mohamed Salem MESSOUD 11714033");
        textCredits.setFont(Font.font("Verdana", 15));
        textCredits.setTextAlignment(TextAlignment.CENTER);
        textCredits.setFill(Color.WHITE);
        
        Text textCredits2 = new Text("Projet LIFAP7 - POO, 2019");
        textCredits2.setFont(Font.font("Verdana", 15));
        textCredits2.setTextAlignment(TextAlignment.CENTER);
        textCredits2.setFill(Color.WHITE);
        
        vBoxBottomPane2.getChildren().addAll(textCredits, textCredits2);
        
        BorderPane pane2 = new BorderPane();
        pane2.setStyle("-fx-background-color: black; -fx-padding: 10 ;");
        pane2.setTop(hBoxTopPane2);
        pane2.setCenter(vBoxCenterPane2);
        pane2.setBottom(vBoxBottomPane2);
        scene2= new Scene(pane2,450,500);
        scene2.getStylesheets().add("mvc/game.css");
        
        stage.setTitle("Projet LIFAP7");
        stage.setResizable(false);
        stage.setScene(scene2);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
