package pyland.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumère toutes les directions connues de Pyland :
 *     {NORTH, EAST, SOUTH, WEST}
 * Une direction peut aussi être obtenue par son nom.
 * @inv <pre>
 *     name() != null
 *     opposite() != null
 *     NORTH.name().equals("north")
 *     EAST.name().equals("east")
 *     SOUTH.name().equals("south")
 *     WEST.name().equals("west")
 *     NORTH.opposite() == SOUTH
 *     EAST.opposite() == WEST
 *     SOUTH.opposite() == NORTH
 *     WEST.opposite() == EAST </pre>
 */
public final class Direction {

    // ATTRIBUTS STATIQUES

    public static final Direction NORTH = new Direction("north");
    public static final Direction EAST  = new Direction("east");
    public static final Direction SOUTH = new Direction("south");
    public static final Direction WEST  = new Direction("west");

    private static final Map DIRECTIONS;
    static {
        DIRECTIONS = new HashMap();
        DIRECTIONS.put("n", NORTH);
        DIRECTIONS.put("e", EAST);
        DIRECTIONS.put("s", SOUTH);
        DIRECTIONS.put("w", WEST);
    }

    private static final Map OPPOSITES;
    static {
        OPPOSITES = new HashMap();
        OPPOSITES.put(NORTH, SOUTH);
        OPPOSITES.put(EAST, WEST);
        OPPOSITES.put(SOUTH, NORTH);
        OPPOSITES.put(WEST, EAST);
     }

    // ATTRIBUTS

    /**
     * Le nom interne de la direction.
     */
    private final String name;

    // CONSTRUCTEURS

    /**
     * Une nouvelle direction.
     * @pre <pre>
     *     name != null </pre>
     * @post <pre>
     *     name().equals(name) </pre>
     */
    private Direction(String name) {
        assert name != null;

        this.name = name;
    }

    // REQUETES

    public String name() {
        return name;
    }

    public Direction opposite() {
        return (Direction) OPPOSITES.get(this);
    }

    // OUTILS

    /**
     * Convertit si possible la chaîne <code>name</code> en Direction.
     * @pre <pre>
     *     name != null </pre>
     * @post <pre>
     *     name.toLowerCase().beginsWith("n") ==> result == NORTH
     *     name.toLowerCase().beginsWith("e") ==> result == EAST
     *     name.toLowerCase().beginsWith("s") ==> result == SOUTH
     *     name.toLowerCase().beginsWith("w") ==> result == WEST
     *     dans tous les autres cas           ==> result == null </pre>
     */
    public static Direction valueOf(String name) {
        if (name == null) {
            throw new AssertionError();
        }

        if (name.equals("")) {
            return null;
        }
        String n = name.substring(0, 1).toLowerCase();
        return (Direction) DIRECTIONS.get(n);
    }

    /**
     * Un tableau sur toutes les directions possibles.
     * @post <pre>
     *     result est un tableau de la forme [NORTH, EAST, SOUTH, WEST] </pre>
     */
    public static Direction[] allDirections() {
        return new Direction[] { NORTH, EAST, SOUTH, WEST };
    }
}
