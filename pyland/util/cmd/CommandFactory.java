package pyland.util.cmd;

import java.lang.reflect.InvocationTargetException;

/**
 * Cette usine à commandes crée des commandes à la demande de l'analyseur.
 */
public final class CommandFactory {

    // CONSTRUCTEURS

    private CommandFactory() {
        // pas d'instanciation externe possible
    }

    /**
     * Pour obtenir une instance de commande en fonction du nom proposé.
     */
    public static ICommand getInstance(String name, String[] args) {
        if ((name == null) || (name.length() == 0)) {
            return new UnknownCommand(args);
        }
        String className =
                CommandFactory.class.getPackage().getName() + "." + name;
        ICommand cmd = null;
        String error = "";
        try {
            if (args == null) {
                args = new String[0];
            }
            Class stringTabClass = Class.forName("[Ljava.lang.String;");
            cmd = (ICommand) Class.forName(className)
                    .getDeclaredConstructor(new Class[] { stringTabClass })
                        .newInstance(new Object[] { args });
        } catch (IllegalArgumentException e) {
            error = "Type des paramètres du constructeur non valide";
        } catch (SecurityException e) {
            error = "Accès à la classe de commande impossible";
        } catch (InstantiationException e) {
            error = "Classe de commande non instanciable";
        } catch (IllegalAccessException e) {
            error = "Accès au constructeur impossible";
        } catch (InvocationTargetException e) {
            error = "Exception levée par le constructeur";
        } catch (NoSuchMethodException e) {
            error = "Constructeur de commande non défini";
        } catch (ClassNotFoundException e) {
            error = "Commande inexistante";
        }
        if (cmd == null) {
            String[] newArgs = new String[args.length + 2];
            newArgs[0] = name;
            System.arraycopy(args, 0, newArgs, 1, args.length);
            newArgs[newArgs.length - 1] = "(" + error + ")";
            return new UnknownCommand(newArgs);
        } else {
            return cmd;
        }
    }

    /**
     * Chaîne de toutes les commandes disponibles avec leur syntaxe.
     */
    public static String getAllCommands() {
        return "go <dir> | help | quit";
    }
}
