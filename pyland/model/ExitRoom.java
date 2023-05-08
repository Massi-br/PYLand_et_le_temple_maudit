package pyland.model;


/**
 * Décrivez votre classe ExitRoom ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
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
     *     fengShuiEffect().contains("Vous êtes sorti des griffes de"
     *             + " PY le maléfique !") </pre>
     */
    public void setVisitor(IPlayer v){
        super.setVisitor(v);
        v.leave();
        fengShuiEffect();
    }
    
    public String fengShuiEffect() {
        return ("Vous êtes sorti des griffes de PY le maléfique !"); 
    }
}
