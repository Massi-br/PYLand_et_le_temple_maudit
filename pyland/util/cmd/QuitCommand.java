package pyland.util.cmd;

import pyland.model.IPlayer;

/**
 * @inv <pre>
 *     argsNb() == 0 </pre>
 */
class QuitCommand extends Command {

    // CONSTRUCTEURS

    QuitCommand(String[] args) {
        super();
    }

    // COMMANDES

    public void executeFor(IPlayer p) {
        if (p == null) {
            throw new AssertionError();
        }

        p.leave();
        setDescription("Vous arrêtez de jouer !");
    }
}
