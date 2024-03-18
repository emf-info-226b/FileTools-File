package ihm;

import java.util.ArrayList;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Interface MVC2 "Ihm pour le contrôleur".
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public interface IIhmCtrl {

    /**
     * Cette méthode est appelée par le contrôleur de l'application pour démarrer l'ihm. En l'occurrence ici on ne fait
     * que de rendre visible notre fenêtre.
     */
    void ihmStart();

    /**
     * Cette méthode permet d'afficher un message d'information à l'utilisateur.
     *
     * @param message le message d'information à afficher
     */
    void messageInformation( String message );

    /**
     * Cette méthode permet d'afficher un message d'erreur à l'utilisateur.
     *
     * @param message le message d'erreur à afficher
     */
    void messageErreur( String message );

    /**
     * Cette méthode permet de demander à l'utilisateur de sélectionner un fichier texte.
     *
     * @param initialFileName le chemin de fichier vers un précédent fichier texte qui avait été sélectionné ou null
     *                        s'il n'y en a pas
     * @return le chemin de fichier vers ule fichier texte sélectionné par l'utilisateur ou null si annullé ou si
     *         problème
     */
    String askUserToSelectTextFile( String initialFileName );

    /**
     * Cette méthode permet de demander à l'utilisateur de sélectionner un fichier quelconque.
     *
     * @param initialFileName le chemin de fichier vers un précédent fichier quelconque qui avait été sélectionné ou
     *                        null s'il n'y en a pas
     * @return le chemin de fichier vers ule fichier quelconque sélectionné par l'utilisateur ou null si annullé ou si
     *         problème
     */
    String askUserToSelectAnyFile( String initialFileName );

    /**
     * Cette méthode permet d'afficher le chemin du fichier texte sélectionné par l'utilisateur.
     *
     * @param filepath le chemin du fichier sélectionné à afficher
     */
    void setFilePath( String filepath );

    /**
     * Cette méthode permet d'afficher le contenu du fichier texte.
     *
     * @param content la liste des lignes contenues dans le fichier texte
     */
    void setTextContent( ArrayList<String> content );

    /**
     * Cette méthode retourne la liste des lignes contenues dans le fichier texte.
     *
     * @return la liste des lignes contenues dans le fichier texte
     */
    ArrayList<String> getTextContent();

}
