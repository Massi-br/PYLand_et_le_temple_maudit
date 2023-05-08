package pyland.model;

/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public interface INormalRoom extends IRoom {

    // ATTRIBUTS STATIQUES

    int POWER_LOSS = 1;

    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old v.getPowerLevel()
     *     getVisitor().getPowerLevel() == max(0, x - POWER_LOSS) </pre>
     */
    void setVisitor(IPlayer v);
}
