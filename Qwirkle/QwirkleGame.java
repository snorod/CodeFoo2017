import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 *
 * @author Stephen Norod
 */
public class QwirkleGame extends Application {

    private Stage window;
    private Scene startScene;
    private Scene boardScene;
    private ToggleGroup whoStarts;
    private Integer whoStartsNum;
    private VBox boardButtons;
    private List<Button> buttonPath;
    private int boardSide;
    private List<QTile> allTiles;
    private static final int SCREEN_WIDTH = 640;
    private String colorArr[] = new String[] {"red", "orange", "yellow", "green", "blue", "violet"};
    private String shapeArr[] = new String[] {"circle", "plus", "square", "star", "diamond", "shuriken"};

    @Override
    public void start(Stage stage) {
        window = stage;
        whoStarts = new ToggleGroup();
        setupStartScene();
        window.setScene(startScene);
        window.setResizable(false);
        window.show();
    }

    private void setupStartScene() {
        boardSide = 19;
        // make a list of tiles
        buttonPath = new ArrayList<Button>();
        allTiles = getTiles();
        Label title = new Label("qwirkle-ish");
        Label swagDaddySteve = new Label("by: stephen norod");
        title.setFont(new Font(100));
        swagDaddySteve.setFont(new Font(20));
        RadioButton player = new RadioButton("player starts");
        player.setUserData(1);
        player.setToggleGroup(whoStarts);
        player.setStyle("-fx-color: white");
        player.setSelected(true);
        RadioButton cpu = new RadioButton("cpu starts");
        cpu.setUserData(0);
        cpu.setToggleGroup(whoStarts);
        cpu.setStyle("-fx-color: white");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(player, cpu);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20.0);
        Button startButton = new Button("start");
        startButton.setStyle("-fx-background-color: #ff6666");
        startButton.setFont(new Font(15));

        // "Create board" button brings up the next screen.
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                setupBoardScene();
                window.setScene(boardScene);
            }
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(title, swagDaddySteve, hbox, startButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);
        StackPane stack = new StackPane();
        stack.getChildren().add(vbox);
        stack.setStyle("-fx-background-color: white");
        startScene = new Scene(stack, QwirkleGame.SCREEN_WIDTH, QwirkleGame.SCREEN_WIDTH);
    }
    private void setupBoardScene() {
        whoStartsNum = (Integer) whoStarts.getSelectedToggle().getUserData();
        Button numButton;
        HBox hbox = new HBox();
        boardButtons = new VBox();
        // NOTE Make the entire game board out of buttons
        for (int i = 0; i < boardSide * boardSide; i++) {
          if (i == 180){
          numButton = new Button("k");
          //numButton.setHighlighted(true);
        } else {
            numButton = new Button("O"); }
            numButton.setFont(new Font(15));
            numButton.setStyle("-fx-background-color: transparent");
            buttonPath.add(numButton);
            hbox.getChildren().add(numButton);
            if (i % boardSide == (boardSide - 1)) {
                hbox.setAlignment(Pos.CENTER);
                boardButtons.getChildren().add(hbox);
                hbox = new HBox();
            }
        }

        boardButtons.setAlignment(Pos.CENTER);
        Button solutionsViewer = new Button("home");
        solutionsViewer.setStyle("-fx-background-color: #ff6666");
        solutionsViewer.setFont(new Font(15));

        Random rand = new Random();
        int n;
        List<QTile> playerHand = new ArrayList<QTile>();
        List<QTile> cpuHand = new ArrayList<QTile>();
        if (whoStartsNum == 1) {
            for (int i = 0; i < 12; i++) {
                n = rand.nextInt(allTiles.size());
                QTile QT = allTiles.get(n);
                if (i < 6) {
                    playerHand.add(QT);
                    allTiles.remove(n);
                } else {
                    cpuHand.add(QT);
                    allTiles.remove(n);
                }
            }
        } else {
            for (int i = 0; i < 12; i++) {
                n = rand.nextInt(allTiles.size());
                QTile QT = allTiles.get(n);
                if (i < 6) {
                    cpuHand.add(QT);
                    allTiles.remove(n);
                } else {
                    playerHand.add(QT);
                    allTiles.remove(n);
                }
            }
        }

        solutionsViewer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                setupStartScene();
                window.setScene(startScene);
            }
        });
        HBox playerHandView = new HBox();
        for (int i = 0; i < 6; i++) {
            QTile QT = playerHand.get(i);
            //Button handPiece = new Button(QT.getColor() + " " + QT.getShape());
            //Image handPiecePic = new Image(getClass().getResourceAsStream("test.png"));
            Button handPiece = new Button(QT.getColor() + " " + QT.getShape());
            handPiece.setStyle("-fx-background-color: transparent");
            //handPiece.setGraphic(new ImageView(handPiecePic));
            playerHandView.getChildren().addAll(handPiece);
        }
        HBox bar = new HBox();
        bar.setSpacing(20);
        bar.getChildren().addAll(playerHandView, solutionsViewer);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(boardButtons, bar);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);
        StackPane stack = new StackPane();
        stack.getChildren().add(vbox);
        stack.setStyle("-fx-background-color: white");
        boardScene = new Scene(stack, QwirkleGame.SCREEN_WIDTH,
        QwirkleGame.SCREEN_WIDTH);
    }

    public List<QTile> getTiles() {
        List<QTile> oneOeight = new ArrayList<QTile>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < colorArr.length; j++) {
                for (int k = 0; k < shapeArr.length; k++) {
                    QTile qT = new QTile(-1, -1, colorArr[j], shapeArr[k]);
                    oneOeight.add(qT);
                }
            }
        }
        return oneOeight;
    }

}
