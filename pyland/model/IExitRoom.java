package pyland.model;

/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public interface IExitRoom extends INormalRoom {

    // COMMANDES

    /**
     * @post <pre>
     *     getVisitor().hasLeft()
     *     fengShuiEffect().contains("Vous êtes sorti des griffes de"
     *             + " PY le maléfique !") </pre>
     */
    void setVisitor(IPlayer v);
}
