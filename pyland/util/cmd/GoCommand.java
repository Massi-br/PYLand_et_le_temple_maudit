package pyland.util.cmd;

import pyland.model.IPlayer;
import pyland.model.IRoom;
import pyland.model.IRoomNetwork;
import pyland.model.RoomNetworkFactory;
import pyland.util.Direction;

/**
 * @inv <pre>
 *     argsNb() == 1
 *     getArgument(0) instanceof Direction </pre>
 */
class GoCommand extends Command {

    // ATTRIBUTS STATIQUES

    /**
     * @pre <pre>
     *     args != null </pre>
     * @post <pre>
     *     args.length == 0 ==> getArgument(0) == null
     *     args.length > 0  ==> getArgument(0) == Direction.valueOf(args[0])
     * </pre>
     */
    GoCommand(String[] args) {
        super(convertArgs(args));
    }

    // COMMANDES

    public void executeFor(IPlayer p) {
        if (p == null) {
            throw new AssertionError();
        }

        // Essayer de quitter la pièce courante.
        Direction dir = (Direction) getArgument(0);
        if (dir == null) {
            setDescription(
                    "Aller... oui, mais où ? Ce n'est pas une direction !"
            );
        } else {
            IRoomNetwork net = RoomNetworkFactory.get();
            IRoom crtRoom = p.getLocation();
            if (!net.canExit(crtRoom, dir)) {
                // il n'y a pas de passage
                setDescription("Aïe ! Il n'y a pas de passage par là !");
            } else {
                // il y a un passage
                crtRoom = net.getRoom(crtRoom, dir);
                p.unsetLocation();
                p.setLocation(crtRoom);
                setDescription("Vous changez de pièce.");
            }
        }
    }

    // OUTILS

    private static Object[] convertArgs(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException();
        }

        if (args.length == 0) {
            return new Object[] { null };
        } else {
            return new Object[] { Direction.valueOf(args[0]) };
        }
    }
}
