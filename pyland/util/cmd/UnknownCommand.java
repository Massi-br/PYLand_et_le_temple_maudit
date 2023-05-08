package pyland.util.cmd;

import pyland.model.IPlayer;

/**
 * @inv <pre>
 *     argsNb() == 1
 *     getArgument(0) != null
 *     getArgument(0) est la phrase non reconnue, tapée par le joueur humain
 *     getArgument(0) se termine par le message d'erreur obtenu lors de la
 *      construction de cette commande inconnue </pre>
 */
class UnknownCommand extends Command {

    // CONSTRUCTERUS

    /**
     * @pre <pre>
     *     args != null
     *     |args| > 0
     *     args[|args| - 1] contient le message d'erreur obtenu lors de la
     *      construction de cette commande inconnue </pre>
     * @post <pre>
     *     getArgument(0) est la concaténation des éléments de args </pre>
     */
    UnknownCommand(String[] args) {
        super(convertArgs(args));
    }

    // COMMANDES

    public void executeFor(IPlayer p) {
        if (p == null) {
            throw new AssertionError();
        }

        setDescription(getArgument(0) + "... ?!");
    }

    // OUTILS

    private static Object[] convertArgs(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException();
        }

        String param = "";
        for (int i = 0; i < args.length; i++) {
            param += " " + args[i];
        }
        return new Object[] { param };
    }
}
