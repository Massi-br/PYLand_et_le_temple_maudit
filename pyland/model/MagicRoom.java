package pyland.model;

/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
 public class MagicRoom extends Room implements IMagicRoom  {
   
     //CONSTRUCTEUR
  
  public MagicRoom (){
        super();
  }
    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old v.getPowerLevel()
     *     x + MIN <= getVisitor().getPowerLevel() <= x + MAX
     *     fengShuiEffect().contains("Vous sentez un flot d'énergie positive"
     *             + " vous envahir.") </pre>
     */
  public void setVisitor(IPlayer v){
      super.setVisitor(v);
      v.setMorePowerful(alea(IMagicRoom.MIN,IMagicRoom.MAX));
      fengShuiEffect();
    }
  
  private int alea(int a , int b){
      assert a<=b;
      return a+(int)(Math.random()* (b-a +1));
    }
    
  public String fengShuiEffect() {
    return ("Vous senter un flot d'énergie positive vous envahir");   
  }
    
}