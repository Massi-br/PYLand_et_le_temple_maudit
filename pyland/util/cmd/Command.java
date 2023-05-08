package pyland.util.cmd;


/**
 * Implantation partielle du type des commandes.
 * Le nom des commandes doit être construit sous la forme :
 *  <code>UnNom + "Command"</code>.
 * @inv <pre>
 *     getName() correspond au nom de la classe privé du suffixe "Command"
 *     describeLastAction() est de la forme : getName() + " : ..." </pre>
 */
abstract class Command implements ICommand {

    // ATTRIBUTS

    private final Object[] args;
    private String description;

    // CONSTRUCTEURS

    /**
     * Une commande munie d'une séquence d'arguments <code>args</code>.
     * Ce sera au constructeur de la sous-classe de fournir un tableau adapté.
     * @pre <pre>
     *     args != null </pre>
     * @post <pre>
     *     argsNb() == args.length
     *     forall i, 0 <= i < |args| : getArgument(i).equals(args[i])
     *     describeLastAction().equals(getName() + " : ") </pre>
     */
    protected Command(Object[] args) {
        if (args == null) {
            throw new AssertionError("Le tableau d'arguments n'existe pas");
        }

        this.args = new Object[args.length];
        System.arraycopy(args, 0, this.args, 0, args.length);
        description = "";
    }

    /**
     * Une commande sans argument.
     * @post <pre>
     *     argsNb() == 0
     *     describeLastAction().equals(getName() + " : ") </pre>
     */
    protected Command() {
        this.args = new Object[0];
        description = "";
    }

    // REQUETES

    public int argsNb() {
        return args.length;
    }

    public String describeLastAction() {
        return getName() + " : " + description;
    }

    public Object getArgument(int i) {
        if (i < 0 || i >= args.length) {
            throw new AssertionError();
        }

        return args[i];
    }

    public String getName() {
        String[] cmd = this.getClass().getName().split("\\.");
        cmd = cmd[cmd.length - 1].split("Command");
        return cmd[0];
    }

    // COMMANDES

    /**
     * Fixe la description de la dernière exécution de cette commande.
     * @pre <pre>
     *     desc != null </pre>
     * @post <pre>
     *     argsNb() == old argsNb()
     *     forall i, 0 <= i < argsNb() : getArgument(i) == old getArgument(i)
     *     describeLastAction().equals(getName() + " : " + desc) </pre>
     */
    protected void setDescription(String desc) {
        if (desc == null) {
            throw new AssertionError();
        }

        description = desc;
    }
}
