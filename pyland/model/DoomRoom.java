package pyland.model;


/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public class DoomRoom extends Room implements IDoomRoom{
    
  private String feng;
    
    //CONSTRUCTEUR
  public DoomRoom(){
        super();
  }
    
    // COMMANDES
    /**
     * @post <pre>
     *     getVisitor().getPowerLevel() == 0
     *     (old v.getPowerLevel() == 0)
     *         ==> getVisitor().isDead()
     *             fengShuiEffect().contains("Vous mourez dans d'atroces"
     *                     + " souffrances.")
     *     (old v.getPowerLevel() > 0)
     *         ==> !getVisitor().isDead()
     *             fengShuiEffect().contains("Vous sentez que seul"
     *                     + " un super hÃ©ros pourra sortir d'ici.") </pre>
     */
  public void setVisitor(IPlayer v){
      super.setVisitor(v);
      int x = v.getPowerLevel();
      if (x == 0) {
          v.kill();
          this.feng="Vous mourez dans d'atroces souffrances.";
      }
      if (x > 0){
          v.setLessPowerful(x);
          this.feng="Vous sentez que seul un super héros pourra sortir d'ici.";
      }
        
  }
  
  public String fengShuiEffect() {
        return this.feng;   
    }
}
