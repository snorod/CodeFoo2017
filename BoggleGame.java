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
 * This class handles all algorithms and graphics for generating and displaying
 * a number-based Boggle board, then finding its solutions.
 *
 * @author Stephen Norod
 */
public class BoggleGame extends Application {

    private Stage window;
    private Scene startScene;
    private Scene boardScene;
    private Scene solutionScene;
    private ToggleGroup gridSize;
    private Integer sideLength;
    private List<Tile> boggleStream;
    private VBox boardButtons;
    private VBox miniBoardButtons;
    private List<Button> buttonPath;
    private int kekSum;
    private List<Tile> kekChain;
    private List<List<Tile>> kekSolutions;
    private static final int SCREEN_WIDTH = 512;

    @Override
    public void start(Stage stage) {
        window = stage;
        gridSize = new ToggleGroup();
        setupStartScene();
        window.setScene(startScene);
        window.setResizable(false);
        window.show();
    }

    /**
     * Creates the main menu, displays three buttons and defines the side length
     * of the board by which grid-size button the user selects, default is 3x3.
     */
    private void setupStartScene() {
        buttonPath = new ArrayList<Button>();
        kekSolutions = new ArrayList<List<Tile>>();
        Label title = new Label("boggle-ish");
        Label swagDaddySteve = new Label("by: stephen norod");
        title.setFont(new Font(100));
        swagDaddySteve.setFont(new Font(20));
        RadioButton grid2 = new RadioButton("2x2");
        grid2.setUserData(2);
        grid2.setToggleGroup(gridSize);
        grid2.setStyle("-fx-color: white");
        RadioButton grid3 = new RadioButton("3x3");
        grid3.setUserData(3);
        grid3.setToggleGroup(gridSize);
        grid3.setStyle("-fx-color: white");
        RadioButton grid4 = new RadioButton("4x4");
        grid4.setUserData(4);
        grid4.setToggleGroup(gridSize);
        grid4.setStyle("-fx-color: white");
        grid3.setSelected(true);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(grid2, grid3, grid4);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20.0);
        Button startButton = new Button("create board");
        startButton.setStyle("-fx-background-color: #85c1e9");
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
        startScene = new Scene(stack, BoggleGame.SCREEN_WIDTH, BoggleGame.SCREEN_WIDTH);
    }

    /**
     * Creates the big board shown on the second screen and a nearly identical
     * board that's smaller so it can be shown on the solutions screen as a reference.
     */
    private void setupBoardScene() {
        sideLength = (Integer) gridSize.getSelectedToggle().getUserData();
        boggleStream = getBoggleStream(sideLength);
        Button numButton;
        Button miniNumButton;
        HBox hbox = new HBox();
        HBox miniHbox = new HBox();
        boardButtons = new VBox();
        miniBoardButtons = new VBox();

        // Creates grid and smaller grid, based on boggleStream defined above.
        for (int i = 0; i < sideLength*sideLength; i++) {
            numButton = new Button(((Integer) boggleStream.get(i).getValue()).toString());
            miniNumButton = new Button(((Integer) boggleStream.get(i).getValue()).toString());
            numButton.setFont(new Font(50));
            miniNumButton.setFont(new Font(20));
            numButton.setStyle("-fx-background-color: transparent");
            miniNumButton.setStyle("-fx-background-color: transparent");
            buttonPath.add(miniNumButton);
            hbox.getChildren().add(numButton);
            miniHbox.getChildren().add(miniNumButton);
            if (i % sideLength == (sideLength - 1)) {
                hbox.setAlignment(Pos.CENTER);
                miniHbox.setAlignment(Pos.CENTER);
                boardButtons.getChildren().add(hbox);
                miniBoardButtons.getChildren().add(miniHbox);
                hbox = new HBox();
                miniHbox = new HBox();
            }
        }

        boardButtons.setAlignment(Pos.CENTER);
        miniBoardButtons.setAlignment(Pos.CENTER);
        Button solutionsViewer = new Button("view solutions");
        solutionsViewer.setStyle("-fx-background-color: #85c1e9");
        solutionsViewer.setFont(new Font(15));

        // "View solutions" button brings up the solutions page.
        solutionsViewer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                setupSolutionScene();
                window.setScene(solutionScene);
            }
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(boardButtons, solutionsViewer);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20.0);
        StackPane stack = new StackPane();
        stack.getChildren().add(vbox);
        stack.setStyle("-fx-background-color: white");
        boardScene = new Scene(stack, BoggleGame.SCREEN_WIDTH,
        BoggleGame.SCREEN_WIDTH);
    }

    /**
     * Calls the getSolutions() method then displays its results.
     */
    private void setupSolutionScene() {
        List<List<Tile>> solutions = getSolutions(sideLength, boggleStream);

        // Converts the list of solutions into a set to eliminate duplicates.
        Set<Set<Tile>> solutionSet = new LinkedHashSet<>();
        Set<Tile> tileSet;
        for (List<Tile> tileList : solutions) {
            tileSet = new LinkedHashSet<>(tileList);
            solutionSet.add(tileSet);
        }

        Label solutionsLabel = new Label("solutions");
        Label directions = new Label("drag the mouse over a solution to see its path");
        solutionsLabel.setFont(new Font(70));
        directions.setFont(new Font(15));
        ListView<Node> solutionDisplay = new ListView<Node>();
        solutionDisplay.setStyle("-fx-control-inner-background: white; -fx-background-color: lightslategray");
        solutionDisplay.setPrefWidth(220.0);

        // Creates a String for each solution and then adds each one to the ListView
        // as a Label, (since a Label is a Node, I can utilize mouse events if I use one).
        for (Set<Tile> solution : solutionSet) {
            String s = "";
            for (Tile t : solution) {
                s += t.getValue() + " + ";
            }
            s = s.substring(0, s.length() - 2);
            s += "= " + sideLength * sideLength;
            Label sAsLabel = new Label(s);

            // This is where the "highlighted path" functionality is created; when
            // a solution is hovered over, the setOnMouseEntered() method sets the background
            // to pink for each button involved in the selected solution...
            sAsLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    for (Tile t: solution) {
                        Button b = buttonPath.get(t.getX() * sideLength + t.getY());
                        b.setStyle("-fx-background-color: #85c1e9");
                    }
                }
            });
            // ...then when the mouse leaves this region, the action is reversed.
            sAsLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    for (Tile t: solution) {
                        Button b = buttonPath.get(t.getX() * sideLength + t.getY());
                        b.setStyle("-fx-background-color: transparent");
                    }
                }
            });

            solutionDisplay.getItems().add(sAsLabel);
        }

        // If there is no solution (common with the 2x2), I don't want to just
        // have a blank rectangle with nothing in it, so I put in a placeholder.
        if (solutions.isEmpty() ) {
            Label noSolutions = new Label("no solutions");
            solutionDisplay.getItems().add(noSolutions);
        }

        HBox hbox = new HBox();
        hbox.getChildren().addAll(solutionDisplay, miniBoardButtons);
        hbox.setAlignment(Pos.CENTER);
        if (sideLength == 2) {
            hbox.setSpacing(75.0);
        } else if (sideLength == 3) {
            hbox.setSpacing(60.0);
        } else if (sideLength == 4) {
            hbox.setSpacing(45.0);
        }
        Button newBoard = new Button("home");
        newBoard.setStyle("-fx-background-color: #85c1e9");
        newBoard.setFont(new Font(15));

        // "Home" button starts up the whole process again.
        newBoard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                setupStartScene();
                window.setScene(startScene);
            }
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(solutionsLabel, directions, hbox, newBoard);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15.0);
        StackPane stack = new StackPane();
        stack.setStyle("-fx-background-color: white");
        stack.getChildren().add(vbox);
        solutionScene = new Scene(stack, BoggleGame.SCREEN_WIDTH,
        BoggleGame.SCREEN_WIDTH);
    }

    /**
     * Function where a group of random numbers is used to make several Tile
     * objects, based on the side length defined by the button on the home screen.
     *
     * @param side the side length of the grid.
     * @return a list of tiles that are constructed with random number values.
     */
    public List<Tile> getBoggleStream(Integer side) {
        List<Tile> boggleStream = new ArrayList<Tile>();
        Random rand = new Random();
        int n;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                n = rand.nextInt(10);
                Tile t = new Tile(i, j, n);
                boggleStream.add(t);
            }
        }
        return boggleStream;
    }

    /**
     * Sets neighbors for each Tile, finds all solutions for the board.
     *
     * @param side the side length of the grid.
     * @param boggleStream a list of random tiles created by getBoggleStream().
     * @return a list of Tile combinations that are correct solutions to the board.
     */
    public List<List<Tile>> getSolutions(Integer side, List<Tile> boggleStream) {
        int x, y;

        // Sets all neighbors and defines each Tile as unvisited.
        for (Tile t : boggleStream) {
            y = t.getY();
            x = t.getX();
            t.setVisited(false);
            for (Tile other : boggleStream) {
                if (Math.abs(x-other.getX()) <= 1 && Math.abs(y-other.getY()) <= 1 &&
                !(x-other.getX() == 0 && y-other.getY() == 0)) {
                    t.setNeighbor(other);
                }
            }
        }

        List<List<Tile>> solutions = new LinkedList<List<Tile>>();

        // Starts a new tree for each Tile on the board.
        for (Tile t : boggleStream) {
            kekChain = new LinkedList<Tile>();
            kekChain.add(t);
            kekSum = t.getValue();
            t.setVisited(true);
            traverse(t);
        }

        return kekSolutions;
    }

    /**
     * Recursive helper function for traversing through the board.
     *
     * @param t the Tile most recently added to the current chain.
     */
    public void traverse(Tile t) {
        for (Tile child : t.getNeighbors()) {

            // For each neighbor, if there is a viable way forward,
            // add it to the chain and continue with it.
            if ((kekSum + child.getValue()) <= (sideLength * sideLength) &&
            !(child.getVisited())) {
                kekSum += child.getValue();
                child.setVisited(true);
                kekChain.add(child);
                if (kekSum == sideLength * sideLength) {
                    List<Tile> kek = new LinkedList<Tile>();
                    for (Tile copy : kekChain) {
                        kek.add(copy);
                    }
                    if (kek.size() >= (sideLength - 1)) {
                        kekSolutions.add(kek);
                    }
                }
                traverse(child);
            }
        }

        // If Tile t does not lead to any more paths, remove it from the chain
        // before coming out of its iteration.
        kekSum -= t.getValue();
        t.setVisited(false);
        kekChain.remove(t);

    }
}
