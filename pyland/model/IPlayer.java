package pyland.model;

/**
 * Modélise les joueurs.
 * Un joueur se déplace de salle en salle à la recherche de la sortie
 *  du labyrinthe.
 * @inv <pre>
 *     forall r in IRoom : this.getLocation() == r ==> this == r.getVisitor()
 *     getHitPoints() >= 0
 *     isDead() <==> getHitPoints() == 0
 *     isDead() ==> hasLeft()
 *     getPowerLevel() >= 0 </pre>
 * @cons <pre>
 *     $DESC$ Un joueur qui n'est pas encore placé dans une salle.
 *     $ARGS$ int hp
 *     $PRE$
 *         hp > 0
 *     $POST$
 *         getLocation() == null
 *         !hasLeft()
 *         getHitPoints() == hp
 *         getPowerLevel() == 0 </pre>
 */
public interface IPlayer {

    // REQUETES

    /**
     * Le nombre de points d'attaque de ce joueur.
     */
    int getHitPoints();

    /**
     * La salle dans laquelle se trouve le joueur.
     */
    IRoom getLocation();

    /**
     * La quantité de superpouvoir du joueur.
     */
    int getPowerLevel();

    /**
     * Indique si le joueur est mort.
     */
    boolean isDead();

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
     *     getLocation() == old getLocation()
     *     getPowerLevel() == old getPowerLevel()
     *     getHitPoints() == old getHitPoints() </pre>
     */
    void leave();

    /**
     * Tue le joueur.
     * @pre
     *     !hasLeft()
     * @post <pre>
     *     getLocation() == old getLocation()
     *     getPowerLevel() == old getPowerLevel()
     *     getHitPoints() == 0
     *     hasLeft() </pre>
     */
    void kill();

    /**
     * Associe la salle <code>r</code> à ce joueur.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() == null && r != null
     *     r.getVisitor() == null || r.getVisitor() == this </pre>
     * @post <pre>
     *     getLocation() == r
     *     l'état du joueur peut avoir changé selon le type de r </pre>
     */
    void setLocation(IRoom r);

    /**
     * Transforme ce joueur en super héro pour q tours.
     * @pre <pre>
     *     q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     !hasLeft()
     *     getLocation() == old getLocation()
     *     getHitPoints() == old getHitPoints()
     *     getPowerLevel() == old getPowerLevel() + q </pre>
     */
    void setMorePowerful(int q);

    /**
     * Fait perdre des super pouvoirs au joueur.
     * @pre <pre>
     *     getPowerLevel() >= q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     !hasLeft()
     *     getLocation() == old getLocation()
     *     getHitPoints() == old getHitPoints()
     *     getPowerLevel() == old getPowerLevel() - q </pre>
     */
    void setLessPowerful(int q);

    /**
     * Augmente le nombre de points d'attaque de ce joueur.
     * @pre <pre>
     *     q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     !hasLeft()
     *     getLocation() == old getLocation()
     *     getPowerLevel() == old getPowerLevel()
     *     getHitPoints() == old getHitPoints() + q </pre>
     */
    void strengthen(int q);

    /**
     * Dissocie ce joueur de sa salle.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() != null </pre>
     * @post <pre>
     *     getLocation() == null
     *     l'état du joueur peut avoir changé selon le type de old getLocation()
     * </pre>
     */
    void unsetLocation();
}
