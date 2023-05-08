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
public class Room implements  IRoom {
    private IPlayer visitor;
    private boolean reccal= false;
    
    public Room(){
        visitor = null;
    }
    // REQUETES
    /**
     * Le joueur qui se trouve dans cette salle.
     */
    public IPlayer getVisitor(){
        return visitor;
    }

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
    public void setVisitor(IPlayer v){
        if(visitor != null || v == null || v.hasLeft() ||(v.getLocation() != null && v.getLocation() != this)){
            throw new AssertionError("");
        }
        visitor = v;
        if(v.getLocation() == null){
            v.setLocation(this);
        }
    }

    /**
     * Dissocie cette salle de son visiteur.
     * @pre <pre>
     *     getVisitor() != null
     *     !getVisitor().hasLeft() </pre>
     * @post <pre>
     *     getVisitor() == null </pre>
     */
    public void unsetVisitor(){
        if(visitor ==null || visitor.hasLeft()){
            throw new AssertionError("");
        }
        if(!reccal){
            reccal =true;
            visitor.unsetLocation();
            reccal= false;
        }
        visitor = null;
        
    }
}
