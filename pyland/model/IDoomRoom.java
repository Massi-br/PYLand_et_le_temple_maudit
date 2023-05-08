package pyland.model;

/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public interface IDoomRoom extends IRoom {

    // COMMANDES

    /**
     * @post <pre>
     *     getVisitor().getPowerLevel() == 0
     *     (old v.getPowerLevel() == 0)
     *         ==> getVisitor().isDead()
     *             fengShuiEffect().contains("Vous mourez dans d'atroces"
     *                     + " souffrances.")
     *     (old v.getPowerLevel() > 0)
     *         ==> !getVisitor().isDead()
     *             fengShuiEffect().contains("Vous sentez que seul"
     *                     + " un super héros pourra sortir d'ici.") </pre>
     */
    void setVisitor(IPlayer v);
}
