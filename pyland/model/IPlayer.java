package pyland.model;

/**
 * Modélise les joueurs.
 * Un joueur se déplace de salle en salle à la recherche de la sortie
 *  du labyrinthe.
 * @inv <pre>
 *     forall r in IRoom :
 *         this.getLocation() == r ==> this == r.getVisitor() </pre>
 * @cons <pre>
 *     $DESC$ Un joueur qui n'est pas encore placé dans une salle.
 *     $ARGS$ -
 *     $POST$
 *         getLocation() == null
 *         !hasLeft() </pre>
 */
public interface IPlayer {

    // REQUETES

    /**
     * La salle dans laquelle se trouve le joueur.
     */
    IRoom getLocation();

    /**
     * Indique si le joueur a arrêté la partie.
     */
    boolean hasLeft();

    // COMMANDES

    /**
     * Fait s'arrêter le joueur.
     * @pre <pre>
     *     !hasLeft() </pre>
     * @post <pre>
     *     hasLeft()
     *     getLocation() == old getLocation() </pre>
     */
    void leave();

    /**
     * Associe la salle <code>r</code> à ce joueur.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() == null && r != null
     *     r.getVisitor() == null || r.getVisitor() == this </pre>
     * @post <pre>
     *     getLocation() == r </pre>
     */
    void setLocation(IRoom r);

    /**
     * Dissocie ce joueur de sa salle.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() != null </pre>
     * @post <pre>
     *     getLocation() == null </pre>
     */
    void unsetLocation();
}
