package sample;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;


public class Main extends Application {

    static int WIDTH = 1800;
    static int HEIGHT = 800;
    static Scene mainScene;
    static GraphicsContext graphicsContext;
    static HashSet<String> currentlyActiveKeys = new HashSet<String>();

    // Forklifts
    static  Image forklift;
    static  Image forklift2;
    static  Image forkliftMINI11;
    static  Image forkliftMINI21;
    static  Image forkliftMINI12;
    static  Image forkliftMINI22;
    static  Image forkliftMINI13;
    static  Image forkliftMINI23;
    static  Image forkliftMINI14;
    static  Image forkliftMINI24;
    static  Image forkliftMINI15;
    static  Image forkliftMINI25;
    static  Image forkliftMINI16;
    static  Image forkliftMINI26;
    static  Image forkliftMINI17;
    static  Image forkliftMINI27;
    static  Image forkliftMINI18;
    static  Image forkliftMINI28;
    static String currentForklift;

    // Etc
    static  Image background;
    static  Image conveyor;
    static Image cover;

    // Cases
    static Image caseOne;
    static Image caseTwo;
    static Image caseThree;
    static Image caseFour;
    static Image caseFive;
    static Image caseSix;
    static Image caseSeven;
    static Image caseEight;
    static double casePoints[][] = new double[120][2];

    // Randoms for cases-spawns
    static Random caseSpawn = new Random();
    static Random caseNumber = new Random();
    static Image casesToSpawn[] = new Image[20];
    static int locOfCases[] = new int[20];
    static Boolean caseNotToSpawn = false;

    static double actualPositionH = 500;
    static double actualPositionW = 100;
    static double conveyorPos = 0.0;


    // True if right, false if left
    static boolean leftOrRight = true;

    @Override
    public void start(Stage mainStage) throws Exception {


        // Declare random case spawn-points

        // X
        /*
        IntStream.range(0, 10).forEach(
                n -> {
                    casePoints[n][0] = 156.5;
                    casePoints[n+10][0] = 210.0;
                    casePoints[n+20][0] = 313.5;
                    casePoints[n+30][0] = 367.5;
                    casePoints[n+40][0] = 472.0;
                    casePoints[n+50][0] = 525.5;
                    casePoints[n+60][0] = 629.0;
                    casePoints[n+70][0] = 682.0;
                    casePoints[n+80][0] = 784.0;
                    casePoints[n+90][0] = 837.0;
                    casePoints[n+100][0] = 945.5;
                    casePoints[n+110][0] = 999.0;
                }
        );*/

        for (int n = 0; n < 109; n += 12) casePoints[n][0] = 156.5;
        for (int n = 1; n < 110; n += 12) casePoints[n][0] = 210.0;
        for (int n = 2; n < 111; n += 12) casePoints[n][0] = 313.5;
        for (int n = 3; n < 112; n += 12) casePoints[n][0] = 367.5;
        for (int n = 4; n < 113; n += 12) casePoints[n][0] = 472.0;
        for (int n = 5; n < 114; n += 12) casePoints[n][0] = 525.5;
        for (int n = 6; n < 115; n += 12) casePoints[n][0] = 629.0;
        for (int n = 7; n < 116; n += 12) casePoints[n][0] = 682.0;
        for (int n = 8; n < 117; n += 12) casePoints[n][0] = 784.0;
        for (int n = 9; n < 118; n += 12) casePoints[n][0] = 837.0;
        for (int n = 10; n < 119; n += 12) casePoints[n][0] = 945.5;
        for (int n = 11; n < 120; n += 12) casePoints[n][0] = 999.0;

        // Y
        IntStream.range(0,120).forEach(
                n -> {
                    if(n<12){
                        casePoints[n][1] = 97.0;
                    } else if(n > 11 && n < 24 ) {
                        casePoints[n][1] = 135.0;
                    } else if(n > 23 && n < 36) {
                        casePoints[n][1] = 189.5;
                    } else if(n > 35 && n < 48) {
                        casePoints[n][1] = 226.0;
                    } else if(n > 47 && n < 60) {
                        casePoints[n][1] = 279.5;
                    } else if(n > 59 && n < 72) {
                        casePoints[n][1] = 316.0;
                    } else if(n > 71 && n < 84) {
                        casePoints[n][1] = 370.0;
                    } else if(n > 83 && n < 96) {
                        casePoints[n][1] = 408.0;
                    } else if(n > 95 && n < 108) {
                        casePoints[n][1] = 460.0;
                    } else if(n > 107 && n < 120) {
                        casePoints[n][1] = 496.5;
                    }

                }
        );





        try {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("sample.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        mainStage.setTitle("Inteligentny wozek widlowy");
       // Group root = new Group();
        mainScene = new Scene(page);
        mainStage.setScene(mainScene);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        page.getChildren().add(canvas);

        prepareActionHandlers();
        graphicsContext = canvas.getGraphicsContext2D();
        loadGraphics();

        /**
         * Main "game" loop
         */
        new AnimationTimer()
        {
            public void handle(long currentNanoTime) {
                tickAndRender();
                setStatement();
                conveyorAnimated();
            }
        }.start();

        mainStage.show();
        } catch (IOException e) {
           e.printStackTrace();
        }

        // Get random spawn points and random cases.
        IntStream.range(0,20).forEach(
                n -> {
                    int i = caseSpawn.nextInt(119) + 1;
                    int j = caseNumber.nextInt(8) + 1;

                    switch (j) {
                        case 1:
                            casesToSpawn[n] = caseOne;
                            break;
                        case 2:
                            casesToSpawn[n] = caseTwo;
                            break;
                        case 3:
                            casesToSpawn[n] = caseThree;
                            break;
                        case 4:
                            casesToSpawn[n] = caseFour;
                            break;
                        case 5:
                            casesToSpawn[n] = caseFive;
                            break;
                        case 6:
                            casesToSpawn[n] = caseSix;
                            break;
                        case 7:
                            casesToSpawn[n] = caseSeven;
                            break;
                        case 8:
                            casesToSpawn[n] = caseEight;
                            break;
                        default:
                            casesToSpawn[n] = caseOne;
                            break;
                    }
                    locOfCases[n] = i;

                }
        );
    }

    private static void prepareActionHandlers()
    {
        mainScene.setOnKeyPressed(event -> currentlyActiveKeys.add(event.getCode().toString()));
        mainScene.setOnKeyReleased(event -> currentlyActiveKeys.remove(event.getCode().toString()));
    }

    private static void loadGraphics()
    {
        forklift = new Image("images/forklift.png");
        forklift2 = new Image("images/forklift2.png");
        forkliftMINI11 = new Image("images/1/forkliftMINI11.png");
        forkliftMINI21 = new Image("images/1/forkliftMINI21.png");
        forkliftMINI12 = new Image("images/2/forkliftMINI12.png");
        forkliftMINI22 = new Image("images/2/forkliftMINI22.png");
        forkliftMINI13 = new Image("images/3/forkliftMINI13.png");
        forkliftMINI23 = new Image("images/3/forkliftMINI23.png");
        forkliftMINI14 = new Image("images/4/forkliftMINI14.png");
        forkliftMINI24 = new Image("images/4/forkliftMINI24.png");
        forkliftMINI15 = new Image("images/5/forkliftMINI15.png");
        forkliftMINI25 = new Image("images/5/forkliftMINI25.png");
        forkliftMINI16 = new Image("images/6/forkliftMINI16.png");
        forkliftMINI26 = new Image("images/6/forkliftMINI26.png");
        forkliftMINI17 = new Image("images/7/forkliftMINI17.png");
        forkliftMINI27 = new Image("images/7/forkliftMINI27.png");
        forkliftMINI18 = new Image("images/8/forkliftMINI18.png");
        forkliftMINI28 = new Image("images/8/forkliftMINI28.png");
        background = new Image("images/background.png");
        conveyor = new Image("images/conveyor.png");
        cover = new Image("images/cover.png");
        caseOne = new Image("images/case1.png");
        caseTwo = new Image("images/case2.png");
        caseThree = new Image("images/case3.png");
        caseFour = new Image("images/case4.png");
        caseFive = new Image("images/case5.png");
        caseSix = new Image("images/case6.png");
        caseSeven = new Image("images/case7.png");
        caseEight = new Image("images/case8.png");

    }

    private static void tickAndRender()
    {
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        graphicsContext.drawImage(background, 0, 0);


        // Spawn Cases.
        IntStream.range(0,19).forEach(
                n -> {
                    double distance = Math.sqrt((Math.pow((actualPositionH - casePoints[locOfCases[n]][1]), 2)) + (Math.pow((actualPositionW - casePoints[locOfCases[n]][0]), 2)));
                    if (distance > 30) {
                        graphicsContext.drawImage(casesToSpawn[n], casePoints[locOfCases[n]][0],
                                casePoints[locOfCases[n]][1]);
                    } else { caseNotToSpawn = true; }
                    if (distance<30 || caseNotToSpawn == true) {
                        //currentForklift = casesToSpawn[n];
                        graphicsContext.drawImage(casesToSpawn[n], actualPositionW +10, actualPositionH);
                    }
                }


        );

        // Arrow keys moving
        if (currentlyActiveKeys.contains("LEFT"))
        {
            actualPositionW = actualPositionW - 1.5;
            graphicsContext.drawImage(forklift2, actualPositionW, actualPositionH);
            leftOrRight = false;
        }
        else if (currentlyActiveKeys.contains("RIGHT"))
        {
            actualPositionW = actualPositionW + 1.5;
            graphicsContext.drawImage(forklift, actualPositionW , actualPositionH);
            leftOrRight = true;
        }
        else if (currentlyActiveKeys.contains("DOWN")) {
            actualPositionH = actualPositionH + 1.5;
            if (leftOrRight == true) {
                graphicsContext.drawImage(forklift, actualPositionW, actualPositionH);
            } else {
                graphicsContext.drawImage(forklift2, actualPositionW, actualPositionH);
            }
        }
        else if (currentlyActiveKeys.contains("UP")) {
            actualPositionH = actualPositionH - 1.5;
            if (leftOrRight == true) {
                graphicsContext.drawImage(forklift, actualPositionW, actualPositionH);
            } else {
                graphicsContext.drawImage(forklift2, actualPositionW, actualPositionH);
            }
       }
        else {
            if (leftOrRight == true) {
                graphicsContext.drawImage(forklift, actualPositionW, actualPositionH);
            } else {
                graphicsContext.drawImage(forklift2, actualPositionW, actualPositionH);
            }
        }
    }

    private static void setStatement() {
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        graphicsContext.setFont(theFont);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(1);


        graphicsContext.fillText("X: " + actualPositionW, 1200, 50);
        graphicsContext.fillText("Y: " + actualPositionH, 1350, 50);

        graphicsContext.fillText("Output:", 1200, 150);
    }

    private static void conveyorAnimated() {

        graphicsContext.drawImage(conveyor, 5, conveyorPos - 600);
        graphicsContext.drawImage(conveyor, 5, conveyorPos);
        conveyorPos = conveyorPos + 0.5;
        graphicsContext.drawImage(cover, 5, 618 );
        if(conveyorPos >= 600) {
            conveyorPos = 0;
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

}
