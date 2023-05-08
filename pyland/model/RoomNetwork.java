package pyland.model;

import java.util.HashMap;
import java.util.Map;

import pyland.util.Direction;

class RoomNetwork implements IRoomNetwork {

    // ATTRIBUTS

    /**
     * Map dont les clés sont de type IRoom et les valeurs de type Map.
     * Pour chaque valeur de type Map, ses clés sont de type Direction et ses
     *  valeurs de type IRoom.
     */
    private final Map data;

    // CONSTRUCTEURS

    RoomNetwork() {
        data = new HashMap();
    }

    // REQUETES

    public boolean canExit(IRoom r, Direction d) {
        if (r == null || d == null) {
            throw new AssertionError();
        }

        return getRoomImpl(r, d) != null;
    }

    public IRoom getRoom(IRoom r, Direction d) {
        if (r == null || d == null) {
            throw new AssertionError();
        }

        return getRoomImpl(r, d);
    }

    // COMMANDES

    public void clear() {
        data.clear();
    }

    public void connect(IRoom r1, Direction d, IRoom r2) {
        if (r1 == null || d == null || r2 == null) {
            throw new AssertionError();
        }

        IRoom x = getRoomImpl(r1, d);
        if (x != r2) {
            if (x != null) {
                unsetRelationInOneWay(x, d.opposite());
            }
            setRelationInOneWay(r1, d, r2);
            IRoom y = getRoomImpl(r2, d.opposite());
            if (y != null) {
                unsetRelationInOneWay(y, d);
            }
            setRelationInOneWay(r2, d.opposite(), r1);
        }
    }

    // OUTILS

    /**
     * La salle à laquelle on accède à partir de r, dans la direction d.
     * NB : result peut être null, cela signifie qu'il n'y a pas de salle
     *  associée à r dans la Direction d.
     * @pre
     *     r != null && d != null
     * @post
     *     data.get(r) == null || ((Map) data.get(r)).get(d) == null
     *         ==> result == null
     *     data.get(r) != null && ((Map) data.get(r)).get(d) != null
     *         ==> result == ((Map) data.get(r)).get(d)
     */
    private IRoom getRoomImpl(IRoom r, Direction d) {
        assert r != null && d != null;

        /*
         * Rappel : m est une Map dont les clés sont de type Direction
         *  et les valeurs de type IRoom
         */
        Map m = (Map) data.get(r);
        IRoom result = null;
        if (m != null) {
            result = (IRoom) m.get(d);
        }
        return result;
    }

    /**
     * Etablit une relation entre src et dest, dans la direction d en partant
     *  de src.
     * L'invariant n'est pas requis en entrée et n'est pas respecté en sortie !
     * @pre
     *     src != null && d != null && dest != null
     *     getRoom(src, d) == null
     * @post
     *     getRoom(src, d) == dest
     */
    private void setRelationInOneWay(IRoom src, Direction d, IRoom dest) {
        assert src != null && d != null && dest != null;
        assert getRoomImpl(src, d) == null;

        Map m = (Map) data.get(src);
        if (m == null) {
            m = new HashMap();
            data.put(src, m);
        }
        m.put(d, dest);
    }

    /**
     * Sachant qu'il existe une relation à partir de r dans la direction d,
     *  supprime uniquement cette relation.
     * L'invariant n'est pas requis en entrée et n'est pas respecté en sortie !
     * @pre
     *     r != null && d != null
     *     getRoom(r, d) != null
     * @post
     *     getRoom(r, d) == null
     */
    private void unsetRelationInOneWay(IRoom r, Direction d) {
        assert r != null && d != null;
        assert getRoomImpl(r, d) != null;

        Map m = (Map) data.get(r);
        m.remove(d);
        if (m.isEmpty()) {
            data.remove(r);
        }
    }
}
