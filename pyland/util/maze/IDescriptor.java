package pyland.util.maze;

interface IDescriptor {

    // ATTRIBUTS STATIQUES

    String EMPTY1 = " ";

    String EMPTY3 = "   ";

    String VISITED1 = ".";

    String VISITED3 = " . ";

    // REQUETES

    String describe(boolean withLight);
}
