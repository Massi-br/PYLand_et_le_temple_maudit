package pyland.model;

/**
 * @inv <pre>
 *     monstersNb() >= 0 </pre>
 *
 * @cons <pre>
 *     $ARGS$ int n
 *     $PRE$  n > 0
 *     $POST$
 *         getVisitor() == null
 *         monstersNb() == n </pre>
 */
public class MonsterRoom extends Room implements IMonsterRoom {
    //ATTRIBUTS
    private int nbMonsters;
    
    private String feng;
    
    /**
     * Constructeur d'objets de classe MonsterRoom
     */
    public MonsterRoom(int n){
        super();
        if (n <= 0){
            throw new AssertionError("une room de monstres doit avoir des monstres, non??!");
        }
        this.nbMonsters = n;
    }
    
    //REQUETES
    /**
     * Le nombre de monstres présents dans cette salle.
     */
    public int monstersNb(){
        return this.nbMonsters;
    }
    
    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old monstersNb()
     *         y ::= old v.getHitPoints()
     *     0 < x <= y
     *        ==> monstersNb() == 0
     *            getVisitor().getHitPoints() == y + x / 2
     *            fengShuiEffect().contains("Vous combattez victorieusement !")
     *     x > y
     *        ==> monstersNb() == x + y / 2
     *            getVisitor().isDead()
     *            fengShuiEffect().contains("Vous succombez"
     *                    + " dans un rÃ¢le affreux...") </pre>
     */
    public void setVisitor(IPlayer v){
        super.setVisitor(v);
        int x = this.monstersNb();
        int y = v.getHitPoints();
        if(x==0){
            this.feng ="t'as déja tout masacré sur ton chemin";
        }
        if (x <= y){
            this.nbMonsters =0;
            v.strengthen(x/2);
            this.feng ="Vous combattez victorieusement !";
        }else {
            x += y/2;  /*est ce que il ya une difference avec this.nbMonsters =x+y/2*/
            v.kill();
            this.feng="Vous succombez dans un râle affreux...";
        }
    }
    
    public String fengShuiEffect() {
        return this.feng;   
    }
    
    /**
     * @post <pre>
     *     monstersNb() == old monstersNb() </pre>
     */
    public void unsetVisitor(){
        super.unsetVisitor();
    }
}
