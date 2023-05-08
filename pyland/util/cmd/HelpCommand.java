package pyland.util.cmd;

import pyland.model.IPlayer;

/**
 * @inv <pre>
 *     argsNb() == 0 </pre>
 */
class HelpCommand extends Command {

    // CONSTRUCTEURS

    HelpCommand(String[] args) {
        super();
    }

    // COMMANDES

    public void executeFor(IPlayer p) {
        if (p == null) {
            throw new AssertionError();
        }

        setDescription("Vous êtes perdu dans pyland ? Ordonnez "
                + CommandFactory.getAllCommands()
        );
    }
}
