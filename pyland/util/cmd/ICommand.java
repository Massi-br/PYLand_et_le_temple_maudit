package pyland.util.cmd;

import pyland.model.IPlayer;

/**
 * Modélise les commandes.
 *
 * @inv <pre>
 *    argsNb() >= 0
 *    describeLastAction() != null
 *    (getName() != null) && !getName().equals("") </pre>
 * @cons <pre>
 * $DESC$
 *     Une commande munie d'une séquence d'arguments dont la représentation
 *      textuelle se trouve dans args.
 * $ARGS$
 *     String[] args
 * $PRE$
 *     args != null
 * $POST$
 *     Let min ::= min(argsNb(), |args|)
 *     forall i, 0 <= i < min :
 *         getArgument(i) est l'objet que représente la chaîne args[i]
 *     forall i, min <= i < argsNb() :
 *         getArgument(i) == null
 *     describeLastAction().equals("") </pre>
 */
public interface ICommand {

    // REQUETES

    /**
     * Le nombre d'arguments de cette commande.
     */
    int argsNb();

    /**
     * Décrit le résultat de la dernière exécution de cette commande.
     */
    String describeLastAction();

    /**
     * Le ième argument de cette commande.
     * @pre <pre>
     *     0 <= i < argsNb() </pre>
     */
    Object getArgument(int i);

    /**
     * Le nom de cette commande (extrait du nom de la classe).
     */
    String getName();

    // COMMANDES

    /**
     * Exécute cette commande pour le joueur <code>p</code>.
     * Il se peut que l'exécution de cette commande ait modifié un ou plusieurs
     *  arguments de la commande...
     * @pre <pre>
     *     p != null </pre>
     * @post <pre>
     *     p a été modifié en accord avec l'exécution de la commande
     *     describeLastAction() reflète l'exécution de cette commande
     *     getName() == old getName()
     *     argsNb() == old argsNb() </pre>
     */
    void executeFor(IPlayer p);
}
