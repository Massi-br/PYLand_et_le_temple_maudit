package pyland.model;

/**
 * @cons <pre>
 *     $POST$
 *         getVisitor() == null </pre>
 */
public class NormalRoom extends Room implements INormalRoom  {
    
     public NormalRoom(){
        super();
    }

    // COMMANDES

    /**
     * @post <pre>
     *     Let x ::= old v.getPowerLevel()
     *     getVisitor().getPowerLevel() == max(0, x - POWER_LOSS) </pre>
     */
    public void setVisitor(IPlayer v){
        super.setVisitor(v);
        int x =v.getPowerLevel();
        if((x - INormalRoom.POWER_LOSS) < 0){
            v.setLessPowerful(x);
        }else{
            v.setLessPowerful(INormalRoom.POWER_LOSS);
        }
        fengShuiEffect();
    }
    public String fengShuiEffect(){
        return "";
    }
}
