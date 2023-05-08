package pyland.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import pyland.util.cmd.CommandFactory;
import pyland.util.cmd.ICommand;

/**
 * Un analyseur lit les caractères tapés par l'utilisateur et les
 *  interprète comme des directions.
 * Chaque appel à <code>readCommandFromInput()</code> permet de lire une
 *  ligne sur le canal d'entrée et de l'interpréter comme une direction à
 *  laquelle on pourra accéder par <code>getNextDirection()</code>.
 */
public class Parser {

    // ATTRIBUTS STATIQUES

    private static final String DIRS = "NnEeSsWw";
    private static final Map CMD_NAMES;
    static {
        CMD_NAMES = new HashMap();
        CMD_NAMES.put("g", "GoCommand");
        CMD_NAMES.put("h", "HelpCommand");
        CMD_NAMES.put("q", "QuitCommand");
    }

    // ATTRIBUTS

    private final BufferedReader input;
    private ICommand command;
    private String name;
    private String[] args;

    // CONSTRUCTEURS

    /**
     * @pre <pre>
     *     input != null </pre>
     */
    public Parser(Reader input) {
        if (input == null) {
            throw new AssertionError("Le canal d'entrée n'existe pas");
        }

        this.input = new BufferedReader(input);
        command = null;
        name = null;
        args = null;
    }

    // REQUETES

    /**
     * La dernière commande lue.
     */
    public ICommand getCurrentCommand() {
        return command;
    }

    // COMMANDES

    /**
     * @post <pre>
     *     Une ligne a été lue sur le canal d'entrée
     *     getCurrentCommand() != null
     *         <==> la ligne lue est de la forme :
     *                  < nom cmd > < arg1 > < arg2 > ... < argn >
     *     getCurrentCommand() == null
     *         <==> la ligne lue est vide || il y a eu une erreur d'E/S
     *     getCurrentCommand() != null
     *         ==> getCurrentCommand().getName().equals(< nom cmd >)
     *             getCurrentCommand().getArgument(i).equals(< argi+1 >) </pre>
     */
    public void readCommandFromInput() throws IOException {
        // réinitialise la commande courante
        command = null;
        // lit une ligne sur le canal d'entrée
        String line = input.readLine();
        if (line != null) {
            line = line.trim();
            if (!line.equals("")) {
                // traite la ligne lue
                setCurrentData(line);
                command = CommandFactory.getInstance(name, args);
            }
        }
    }

    // OUTILS

    private void setCurrentData(String line) {
        assert line != null;

        String[] words = line.split("\\s+");
        // n -> go north, e -> go east, s -> go south, w -> go west
        if (words.length == 1 && DIRS.indexOf(words[0].charAt(0)) != -1) {
            name = "g";
            args = new String[] { String.valueOf(words[0].charAt(0)) };
        } else {
            name = String.valueOf(words[0].charAt(0)).toLowerCase();
            args = new String[words.length - 1];
            System.arraycopy(words, 1, args, 0, words.length - 1);
        }
        name = (String) CMD_NAMES.get(name);
    }
}
