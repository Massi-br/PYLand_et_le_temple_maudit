package pyland.model;


/**
 * Modélise les salles du labyrinthe.
 * Les salles sont reliées entre elles et le joueur les traverse.
 * @inv <pre>
 *     forall p in IPlayer : this.getVisitor() == p ==> this == p.getLocation()
 *     fengShuiEffect() != null </pre>
 */
public abstract class Room implements IRoom {
    
    private IPlayer getVisitor;
    private boolean reccall =false;
    
    
    /**
     * Constructeur de l'objet Room 
     */
    public Room(){
        this.getVisitor = null;
    }

    // REQUETES
    /**
     * Une chaîne décrivant l'effet feng shui de cette salle sur son visiteur.
     */
    public abstract String fengShuiEffect();
    
    /**
     * Le joueur qui se trouve dans cette salle.
     */
    public IPlayer getVisitor(){
        return this.getVisitor;
    }

    // COMMANDES

    /**
     * Associe le visiteur <code>v</code> Ã  cette salle.
     * @pre <pre>
     *     getVisitor() == null
     *     v != null && !v.hasLeft()
     *     v.getLocation() == null || v.getLocation() == this </pre>
     * @post <pre>
     *     getVisitor() == v
     *     fengShuiEffect() reflète l'effet feng shui sur getVisitor() </pre>
     */
    public void setVisitor(IPlayer v){
        if(getVisitor != null || v == null || v.hasLeft() || v.getLocation() != null && v.getLocation() != this){
            throw new AssertionError("erreur d'association ");
        }
        this.getVisitor = v;
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
     *     getVisitor() == null
     *     fengShuiEffect().equals("") </pre>
     */
    public void unsetVisitor(){
        if( getVisitor == null || getVisitor.hasLeft() ){
            throw new AssertionError("Erreur de disassociation");
        }
        if(!reccall){
            reccall = true;
            getVisitor().unsetLocation();
            reccall =false;
        }
        this.getVisitor = null;
    }
}
