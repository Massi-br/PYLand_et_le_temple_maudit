package pyland.model;


/**
 * D�crivez votre classe ExitRoom ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public class ExitRoom extends Room implements IExitRoom{

    /**
     * Constructeur d'objets de classe ExitRoom
     */
    public ExitRoom(){
        super();
    }
    
    // COMMANDES

    /**
     * @post <pre>
     *     getVisitor().hasLeft()
     *     fengShuiEffect().contains("Vous �tes sorti des griffes de"
     *             + " PY le mal�fique !") </pre>
     */
    public void setVisitor(IPlayer v){
        super.setVisitor(v);
        v.leave();
        fengShuiEffect();
    }
    
    public String fengShuiEffect() {
        return ("Vous �tes sorti des griffes de PY le mal�fique !"); 
    }
}
