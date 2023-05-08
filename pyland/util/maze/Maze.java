package pyland.util.maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import pyland.model.IPlayer;
import pyland.model.IRoom;
import pyland.model.IRoomNetwork;
import pyland.model.RoomNetworkFactory;
import pyland.util.Direction;
import pyland.util.RoomNotFoundException;

/**
 * Le labyrinthe des pièces, rectangulaire (rows * cols) parfait (toute
 *  pièce est accessible) et 1-connexe (toute pièce est accessible par un
 *  seul chemin).
 * @inv <pre>
 *     colsNb() >= MIN_SIZE
 *     rowsNb() >= MIN_SIZE
 *     entry() != null
 *     exit() != null </pre>
 */
public class Maze {

    // ATTRIBUTS STATIQUES

    public static final int MIN_SIZE = 5;

    private static final int ROW = 0;
    private static final int COL = 1;
    private static final float SUB_TYPE_RATIO = 0.4f;
    private static final float MORE_CONNEXIONS = 0.15f;

    // ATTRIBUTS

    /**
     * Le tableau des pièces.
     */
    private MazeRoom[][] maze;

    /**
     * Le nombre de lignes de pièces.
     */
    private final int rows;

    /**
     * Le nombre de colonnes de pièces.
     */
    private final int cols;

    /**
     * Descripteur de labyrinthe, pour l'affichage.
     */
    private IDescriptor mazeDesc;

    /**
     * Indique si le labyrinthe est éclairé.
     */
    private boolean enlightened;

    // CONSTRUCTEURS

    /**
     * Un labyrinthe à n lignes et m colonnes.
     * @pre <pre>
     *     n >= MIN_SIZE && m >= MIN_SIZE </pre>
     * @post <pre>
     *     rowsNb() == n
     *     colsNb() == m </pre>
     */
    public Maze(int n, int m) {
        if ((n < MIN_SIZE) || (m < MIN_SIZE)) {
            throw new AssertionError();
        }

        rows = n;
        cols = m;
        maze = null;
        mazeDesc = null;
        enlightened = false;
    }

    // REQUETES

    public int rowsNb() {
        return rows;
    }

    public int colsNb() {
        return cols;
    }

    /**
     * L'entrée du labyrinthe : la salle en bas à gauche.
     */
    public IRoom entry() {
        if (maze != null) {
            return maze[rows - 1][0].getRoom();
        } else {
            return null;
        }
    }

    /**
     * La sortie du labyrinthe : la salle en haut à droite.
     */
    public IRoom exit() {
        if (maze != null) {
            return maze[0][cols - 1].getRoom();
        } else {
            return null;
        }
    }

    /**
     * Une chaîne qui décrit le labyrinthe.
     */
    public String describe() {
        return mazeDesc.describe(enlightened);
    }

    // COMMANDES

    /**
     * Eteint le labyrinthe (on ne voit que la partie déjà visitée du
     *  labyrinthe).
     */
    public void lightOff() {
        enlightened = false;
    }

    /**
     * Allume le labyrinthe (on voit tout le labyrinthe).
     */
    public void lightOn() {
        enlightened = true;
    }

    /**
     * @post <pre>
     *     Le labyrinthe est constitué de nouvelles pièces dont les parois ont
     *      éventuellement été creusées pour constituer un labyrinthe parfait
     *      1-connexe. </pre>
     */
    public void build() {
        initRooms();
        buildMaze();
        mazeDesc = new MazeDescriptor(this);
    }

    /**
     * Marque la pièce r comme visitée (utilisé lors de l'affichage du
     *  labyrinthe).
     */
    public void mark(IRoom r) {
        int[] coord = find(r);
        if (coord == null) {
            throw new RoomNotFoundException();
        }

        maze[coord[ROW]][coord[COL]].setVisited();
    }

    // OUTILS

    protected MazeRoom get(int i, int j) {
        return maze[i][j];
    }

    boolean powerPlayerAround(int i, int j) {
        for (int r = -1; r < 2; r++) {
            for (int c = -1; c < 2; c++) {
                if (r == 0 && c == 0) {
                    continue;
                }
                int row = i + r;
                int col = j + c;
                if (row >= 0 && row < rowsNb()
                        && col >= 0 && col < colsNb()) {
                    IPlayer p = maze[row][col].getRoom().getVisitor();
                    if (p != null && p.getPowerLevel() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Détecte les coordonnées (dans maze) de la pièce r.
     * Retourne null si pas trouvée.
     */
    private int[] find(IRoom r) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j].getRoom() == r) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }

    /**
     * Alloue des salles toutes neuves au tableau maze en vue de la construction
     *  du labyrinthe.
     */
    private void initRooms() {
        Set[] specials = getSpecialNumbers();
        maze = new MazeRoom[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Integer x = new Integer(i * cols + j);
                if (specials[0].contains(x)) {
                    maze[i][j] = MazeRoom.createMagicRoom(this, i, j);
                } else if (specials[1].contains(x)) {
                    maze[i][j] = MazeRoom.createDoomRoom(this, i, j);
                } else if (specials[2].contains(x)) {
                    maze[i][j] = MazeRoom.createMonsterRoom(this, i, j);
                } else {
                    maze[i][j] = MazeRoom.createNormalRoom(this, i, j);
                }
            }
        }
        maze[0][cols - 1] = MazeRoom.createExitRoom(this, 0, cols - 1);
    }

    /**
     * Retourne 3 ensembles de coordonnées aléatoires (sous forme d'entiers).
     * Chaque élément entier n devra être décodé ainsi :
     * - row = n / cols
     * - col = n % cols
     * Les ensembles sont deux à deux disjoints.
     * Le premier donne les coordonnées des MagicRooms.
     * Le deuxième donne les coordonnées des DoomRooms.
     * Le dernier donne les coordonnées des MonsterRooms.
     */
    private Set[] getSpecialNumbers() {
        final int size = rows * cols;
        final int entry = size - cols;
        final int exit = cols - 1;

        int specialRoomsNb = (int) (size * SUB_TYPE_RATIO);
        final int[] numbers = new int[] {
                4 * specialRoomsNb / 10,
                specialRoomsNb / 5,
                4 * specialRoomsNb / 10
        };
        Set[] result = new Set[3];
        specialRoomsNb = 0;
        for (int i = 0; i < numbers.length; i++) {
            result[i] = new HashSet(numbers[i]);
            specialRoomsNb += numbers[i];
        }

        Set temp = new HashSet(specialRoomsNb);
        for (int i = 0; i < numbers.length; i++) {
            while (result[i].size() != numbers[i]) {
                int k = (int) (Math.random() * size);
                Integer x = new Integer(k);
                if (k != entry && k != exit && !temp.contains(x)
                        && (i == 0 || !nearEntry(k))) {
                    temp.add(x);
                    result[i].add(x);
                }
            }
        }
        return result;
    }

    private boolean nearEntry(int x) {
        final int entry = (rows - 1) * cols;
        return x == entry + 1 || x == entry - cols;
    }

    /**
     * Casse les murs aléatoirement pour construire un labyrinthe parfait
     *  1-connexe.
     * Puis casse encore quelques cloisons pour que ce soit plus fun.
     */
    private void buildMaze() {
        // la pile qui mémorise le chemin parcouru
        Stack stack = new Stack();
        // coordonnées de la pièce courante (en haut à gauche)
        int[] crt = new int[] { 0, 0 };
        // connecter la pièce courante
        connect(crt);
        // tant que l'exploration n'est pas terminée
        while (crt != null) {
            // obtenir ses voisines déconnectées (nord, est, sud, ouest)
            List neighbours = getDisconnectedNeighbours(crt);
            if (neighbours.size() > 0) {
                // il y a des pièces voisines déconnectées
                // en choisir une au hasard
                int randomIndex = (int) (Math.random() * neighbours.size());
                int[] selected = (int[]) neighbours.get(randomIndex);
                // la connecter
                connect(selected);
                // casser le mur entre la pièce courante et cette voisine
                dig(crt, selected);
                // rajouter la pièce courante au chemin parcouru
                stack.push(crt);
                // aller dans la pièce voisine
                crt = selected;
            } else {
                // il n'y a pas de pièce voisine
                if (!stack.empty()) {
                    // on n'est pas revenu au point de départ :
                    // on recule d'une pièce
                    crt = (int[]) stack.pop();
                } else {
                    // la pile est vide : l'exploration est terminée
                    crt = null;
                }
            }
        }
        createSomeMoreConnexions();
    }

    /**
     * Casse quelques cloisons en plus entre les salles pour avoir un labyrinthe
     *  imparfait, c'est-à-dire qu'une salle peut être accédée par plusieurs
     *  chemins.
     * C'est moins parfait mais c'est plus sympa.
     */
    private void createSomeMoreConnexions() {
        final int n = (int) (MORE_CONNEXIONS * rows * cols);
        int i = 0;
        while (i < n) {
            int x = (int) (Math.random() * rows * cols);
            int[] crt = { x / cols, x % cols };
            List neighbours = getPossibleNeighbours(crt);
            if (neighbours.size() > 0) {
                int index = (int) (Math.random() * neighbours.size());
                int[] neighbour = (int[]) neighbours.get(index);
                dig(crt, neighbour);
                i += 1;
            }
        }
    }

    /**
     * Retourne la liste des voisines de la salle située en cell, dont les
     *  cloisons avec cette salle ne sont pas encore percées.
     */
    private List getPossibleNeighbours(int[] cell) {
        IRoomNetwork net = RoomNetworkFactory.get();
        List result = new ArrayList();
        IRoom r = maze[cell[ROW]][cell[COL]].getRoom();
        // voisine au nord
        if (cell[ROW] > 0 && !net.canExit(r, Direction.NORTH)) {
            result.add(new int[] { cell[ROW] - 1, cell[COL] });
        }
        // voisine à l'est
        if (cell[COL] < cols - 1 && !net.canExit(r, Direction.EAST)) {
            result.add(new int[] { cell[ROW], cell[COL] + 1 });
        }
        // voisine au sud
        if (cell[ROW] < rows - 1 && !net.canExit(r, Direction.SOUTH)) {
            result.add(new int[] { cell[ROW] + 1, cell[COL] });
        }
        // voisine à l'ouest
        if (cell[COL] > 0 && !net.canExit(r, Direction.WEST)) {
            result.add(new int[] { cell[ROW], cell[COL] - 1 });
        }
        return result;
    }

    /**
     * Connecte la pièce en coordonnées cell.
     */
    private void connect(int[] cell) {
        maze[cell[ROW]][cell[COL]].setConnected();
    }

    /**
     * Retourne la liste des pièces voisines de cell non connectées à
     *  l'intérieur du labyrinthe.
     */
    private List getDisconnectedNeighbours(int[] cell) {
        List result = new ArrayList();
        // voisine au nord
        if ((cell[ROW] > 0)
                && (!maze[cell[ROW] - 1][cell[COL]].isConnected())) {
            result.add(new int[] { cell[ROW] - 1, cell[COL] });
        }
        // voisine à l'est
        if ((cell[COL] < cols - 1)
                && (!maze[cell[ROW]][cell[COL] + 1].isConnected())) {
            result.add(new int[] { cell[ROW], cell[COL] + 1 });
        }
        // voisine au sud
        if ((cell[ROW] < rows - 1)
                && (!maze[cell[ROW] + 1][cell[COL]].isConnected())) {
            result.add(new int[] { cell[ROW] + 1, cell[COL] });
        }
        // voisine à l'ouest
        if ((cell[COL] > 0)
                && (!maze[cell[ROW]][cell[COL] - 1].isConnected())) {
            result.add(new int[] { cell[ROW], cell[COL] - 1 });
        }
        return result;
    }

    /**
     * Creuse un passage entre les salles aux positions src et dest sur
     *  le labyrinthe.
     */
    private void dig(int[] src, int[] dest) {
        Direction dir = null;
        int deltaRow = dest[ROW] - src[ROW];
        if (deltaRow < 0) {
            dir = Direction.NORTH;
        } else if (deltaRow > 0) {
            dir = Direction.SOUTH;
        } else {
            dir = (dest[COL] - src[COL] > 0) ? Direction.EAST : Direction.WEST;
        }
        IRoom s = maze[src[ROW]][src[COL]].getRoom();
        IRoom d = maze[dest[ROW]][dest[COL]].getRoom();
        RoomNetworkFactory.get().connect(s, dir, d);
    }
}
