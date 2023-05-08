package pyland.model;

/**
 * Modélise les salles du labyrinthe.
 * Les salles sont reliées entre elles et le joueur les traverse.
 * @inv <pre>
 *     forall p in IPlayer :
 *         this.getVisitor() == p ==> this == p.getLocation() </pre>
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public interface IRoom {

    // REQUETES

    /**
     * Le joueur qui se trouve dans cette salle.
     */
    IPlayer getVisitor();

    // COMMANDES

    /**
     * Associe le visiteur <code>v</code> à cette salle.
     * @pre <pre>
     *     getVisitor() == null
     *     v != null && !v.hasLeft()
     *     v.getLocation() == null || v.getLocation() == this </pre>
     * @post <pre>
     *     getVisitor() == v </pre>
     */
    void setVisitor(IPlayer v);

    /**
     * Dissocie cette salle de son visiteur.
     * @pre <pre>
     *     getVisitor() != null
     *     !getVisitor().hasLeft() </pre>
     * @post <pre>
     *     getVisitor() == null </pre>
     */
    void unsetVisitor();
}
