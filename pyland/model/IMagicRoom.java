package pyland.model;

/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public interface IMagicRoom extends IRoom {

    // ATTRIBUTS STATIQUES

    int MIN = 5;
    int MAX = 10;

    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old v.getPowerLevel()
     *     x + MIN <= getVisitor().getPowerLevel() <= x + MAX
     *     fengShuiEffect().contains("Vous sentez un flot d'énergie positive"
     *             + " vous envahir.") </pre>
     */
    void setVisitor(IPlayer v);
}
