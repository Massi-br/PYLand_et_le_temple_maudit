package pyland.model;

/**
 * Modélise les joueurs.
 * Un joueur se déplace de salle en salle Ã  la recherche de la sortie
 *  du labyrinthe.
 * @inv <pre>
 *     forall r in IRoom : this.getLocation() == r ==> this == r.getVisitor()
 *     getHitPoints() >= 0
 *     isDead() <==> getHitPoints() == 0
 *     isDead() ==> hasLeft()
 *     getPowerLevel() >= 0 </pre>
 * @cons <pre>
 *     $DESC$ Un joueur qui n'est pas encore placé dans une salle.
 *     $ARGS$ int hp
 *     $PRE$
 *         hp > 0
 *     $POST$
 *         getLocation() == null
 *         !hasLeft()
 *         getHitPoints() == hp
 *         getPowerLevel() == 0 </pre>
 */
public class Player implements IPlayer {
    
    //VARIABLE D'INSTANCE
    
    private boolean hasLeft ;
    
    private IRoom location ;
    
    private int hitPoints;
    
    private int powerLevel;
    
    private boolean reccall =false;
    
    
    /**
     * CONSTRUCTEUR DE L'OBJET PLAYER
     */
    public Player(int hp){
       if(hp <= 0){
        throw new AssertionError("le joueur doit avoir des points de vie");
       }
        this.location = null;
        this.hasLeft = false;
        this.hitPoints = hp;
        this.powerLevel = 0;
    }

    // REQUETES
    /**
     * Le nombre de points d'attaque de ce joueur.
     */
    public int getHitPoints(){
        return this.hitPoints;
    }
    /**
     * La salle dans laquelle se trouve le joueur.
     */
    public IRoom getLocation(){
        return this.location;
    }
    /**
     * La quantité de superpouvoir du joueur.
     */
    public int getPowerLevel(){
       return this.powerLevel; 
    }

    /**
     * Indique si le joueur est mort.
     */
    public boolean isDead(){
      return  this.getHitPoints() == 0;
    }

    /**
     * Indique si le joueur a arrété la partie.
     */
    public boolean hasLeft(){
        return  (this.hasLeft == true || this.isDead() == true ) ? true : false;
    }

    // COMMANDES

    /**
     * Fait s'arréter le joueur.
     * @pre <pre>
     *     !hasLeft() </pre>
     * @post <pre>
     *     hasLeft()
     *     getLocation() == old getLocation()
     *     getPowerLevel() == old getPowerLevel()
     *     getHitPoints() == old getHitPoints() </pre>
     */
    public void leave(){
        if(hasLeft){
             throw new AssertionError("le joueur est déja parti");
        }
        this.hasLeft = true;
    }
    /**
     * Tue le joueur.
     * @pre
     *     !hasLeft()
     * @post <pre>
     *     getLocation() == old getLocation()
     *     getPowerLevel() == old getPowerLevel()
     *     getHitPoints() == 0
     *     hasLeft() </pre>
     */
    public void kill(){
        if (hasLeft){
            throw new AssertionError("le joueur est déja parti");
        }
        this.hitPoints = 0;
        this.hasLeft =true;
    }

    /**
     * Associe la salle <code>r</code> Ã  ce joueur.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() == null && r != null
     *     r.getVisitor() == null || r.getVisitor() == this </pre>
     * @post <pre>
     *     getLocation() == r
     *     l'état du joueur peut avoir changé selon le type de r </pre>
     */
    public void setLocation(IRoom r){
        if (hasLeft || location != null || r == null || (r.getVisitor() != null) && (r.getVisitor() != this) ){
            throw new AssertionError("Erreur d'association");
        }
        this.location = r;
        if(r.getVisitor() == null){
            r.setVisitor(this);
        }
    }

    /**
     * Transforme ce joueur en super héro pour q tours.
     * @pre <pre>
     *     q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     !hasLeft()
     *     getLocation() == old getLocation()
     *     getHitPoints() == old getHitPoints()
     *     getPowerLevel() == old getPowerLevel() + q </pre>
     */
    public void setMorePowerful(int q){
        if(q < 0 || hasLeft){
            throw new AssertionError("on peut pas transformer le joueur en super-hero");
        }
        this.powerLevel += q;
    }

    /**
     * Fait perdre des super pouvoirs au joueur.
     * @pre <pre>
     *     getPowerLevel() >= q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     !hasLeft()
     *     getLocation() == old getLocation()
     *     getHitPoints() == old getHitPoints()
     *     getPowerLevel() == old getPowerLevel() - q </pre>
     */
    public void setLessPowerful(int q){
        if (q < 0 || q > powerLevel){
            throw new AssertionError("il faut voir le niveau d'énergie ");
        }
        if (hasLeft){
            throw new AssertionError("le joueur n'est plus là");
        }
        this.powerLevel -=q ;
    }

    /**
     * Augmente le nombre de points d'attaque de ce joueur.
     * @pre <pre>
     *     q >= 0
     *     !hasLeft() </pre>
     * @post <pre>
     *     !hasLeft()
     *     getLocation() == old getLocation()
     *     getPowerLevel() == old getPowerLevel()
     *     getHitPoints() == old getHitPoints() + q </pre>
     */
    public void strengthen(int q){
       if(q < 0){
           throw new AssertionError("Energie négatif ??!");
       }
       if (hasLeft){
            throw new AssertionError("le joueur n'est plus là");
       }
       this.hitPoints += q;
    }
    
    /**
     * Dissocie ce joueur de sa salle.
     * @pre <pre>
     *     !hasLeft()
     *     getLocation() != null </pre>
     * @post <pre>
     *     getLocation() == null
     *     l'état du joueur peut avoir changé selon le type de old getLocation()
     * </pre>
     */
    public void  unsetLocation(){
        if(hasLeft || location == null ){
            throw new AssertionError("impossible de dissocier le joueur");
        }
        if (!reccall){
            reccall =true;
            getLocation().unsetVisitor();
            reccall = false;
        }
        this.location = null;
    }
}
