package pyland.model;

/**
 * Fabrique du réseau des salles.
 * Cette classe est non instanciable car c'est une fabrique.
 * Elle est dotée d'une méthode statique qui donne accès à l'instance de
 *  IRoomNetwork nécessaire au jeu sans que l'on ait connaissance de la classe
 *  de cette instance.
 */
public final class RoomNetworkFactory {

    // ATTRIBUTS STATIQUES

    private static final IRoomNetwork NETWORK = new RoomNetwork();

    // CONSTRUCTEURS

    private RoomNetworkFactory() {
        // pas d'instanciation possible
    }

    public static IRoomNetwork get() {
        return NETWORK;
    }
}
