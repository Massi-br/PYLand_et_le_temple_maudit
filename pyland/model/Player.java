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
public class Player implements IPlayer {

    private IRoom location;
    private boolean hasleft;
    private boolean reccal = false;

    public Player(){
        location = null;
        hasleft = false;
    }

    // REQUETES

    /**
     * La salle dans laquelle se trouve le joueur.
     */
    public IRoom getLocation(){
        return location;
    }

    /**
     * Indique si le joueur a arrêté la partie.
     */
    public boolean hasLeft(){
        return hasleft;
    }

    // COMMANDES

    /**
     * Fait s'arrêter le joueur.
     * @pre <pre>
     *     !hasLeft() </pre>
     * @post <pre>
     *     hasLeft()
     *     getLocation() == old getLocation() </pre>
     */
    public void leave(){
        if(hasLeft()){
            throw new AssertionError("");
        }
        hasleft =true;
    }

    /**
     * Associe la salle <code>r</code> à ce joueur.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() == null && r != null
     *     r.getVisitor() == null || r.getVisitor() == this </pre>
     * @post <pre>
     *     getLocation() == r </pre>
     */
    public void setLocation(IRoom r){
        if(hasLeft() || location != null || r == null || (r.getVisitor() != null && r.getVisitor() != this) ){
            throw new AssertionError("");
        }
        location = r ;
        if(r.getVisitor() == null){
            r.setVisitor(this);
        }
    }

    /**
     * Dissocie ce joueur de sa salle.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() != null </pre>
     * @post <pre>
     *     getLocation() == null </pre>
     */
    public void unsetLocation(){
        if(hasLeft() || location == null){
            throw new AssertionError("");
        }      
        if(!reccal){
            reccal =true;
            location.unsetVisitor();
            reccal= false;
        }
        location = null;
    }
}
