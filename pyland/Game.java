package pyland;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import pyland.model.IPlayer;
import pyland.model.Player;
import pyland.util.Parser;
import pyland.util.cmd.ICommand;
import pyland.util.maze.Maze;

public class Game {

    // ATTRIBUTS STATIQUES

    private static final int MIN = Maze.MIN_SIZE;

    // ATTRIBUTS

    private final Maze maze;
    private final Parser parser;
    private final PrintStream output;

    private String lastReport;

    // CONSTRUCTEURS

    public Game(int n, int m) {
        if (n < MIN || m < MIN) {
            throw new AssertionError();
        }

        maze = new Maze(n, m);
        parser = new Parser(new InputStreamReader(System.in));
        output = System.out;
        lastReport = "";
    }

    // COMMANDES

    public void play() {
        maze.build();
        IPlayer player = new Player();
        player.setLocation(maze.entry());
        maze.mark(maze.entry());
        output.println(maze.describe());
        while (!player.hasLeft()) {
            output.print(getPrompt());
            readNextCommandFromInput();
            executeLastCommandFor(player);
            printLastReport();
        }
        printFinalReport(player);
    }

    // OUTILS

    private String getPrompt() {
        return "pyland > ";
    }

    private void readNextCommandFromInput() {
        try {
            parser.readCommandFromInput();
        } catch (IOException e) {
            System.err.println(
                    "Erreur en cours de lecture : " + e.getMessage());
        }
    }

    private void executeLastCommandFor(IPlayer p) {
        lastReport = "";
        ICommand cmd = parser.getCurrentCommand();
        if (cmd != null) {
            cmd.executeFor(p);
            if (p.hasLeft()) {
                maze.lightOn();
            } else {
                maze.mark(p.getLocation());
            }
            lastReport += cmd.describeLastAction() + "\n" + maze.describe();
        }
    }

    private void printLastReport() {
        output.println(lastReport);
    }

    private void printFinalReport(IPlayer p) {
        if (p.getLocation() != maze.exit()) {
            output.println("Bouh le lâche ! Vous avez abandonné !");
        } else {
            output.println("Bravo ! Vous avez gagné, vous êtes sorti"
                    + " des griffes de PY le maléfique !"
            );
        }
    }

    // POINT D'ENTREE

    public static void main(String[] args) {
        new Game(5, 8).play();
    }
}
