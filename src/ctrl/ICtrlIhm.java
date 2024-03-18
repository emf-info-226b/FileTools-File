package ctrl;

import java.util.ArrayList;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Interface MVC2 "Contrôleur pour l'Ihm".
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public interface ICtrlIhm {

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'application est en train de se fermer. Cela permet de
     * sauvegarder l'état de l'application.
     */
    void ihmExiting();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de sélectionner
     * un fichier texte.
     */
    void SelectFileActionPerformed();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de lire le
     * fichier texte.
     */
    void ReadTextFileActionPerformed();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant d'écrire le
     * fichier texte.
     */
    void WriteTextFileActionPerformed();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de compresser
     * un fichier.
     */
    void CompressActionPerformed();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de décompresser
     * un fichier.
     */
    void DecompressActionPerformed();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de crypter un
     * fichier.
     */
    void EncryptActionPerformed();

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de décrypter un
     * fichier.
     */
    void DecryptActionPerformed();

    /**
     * Cette méthode retourne la liste de tous les algorithmes de compression disponibles.
     *
     * @return la liste de tous les algorithmes de compression disponibles
     */
    ArrayList<String> getCompressionAlgorithms();

    /**
     * Cette méthode permets de sélection l'un ou l'autre des algorithmes de compression disponibles.
     *
     * @param selected l'algorithme de compression sélectionné
     */
    void setSelectedCompressionAlgorithm( String selected );

    /**
     * Cette méthode permets de savoir quel est l'algorithme de compression sélectionné.
     *
     * @return l'algorithme de compression sélectionné
     */
    String getSelectedCompressionAlgorithm();

    /**
     * Cette méthode retourne la liste de tous les algorithmes de cryptage disponibles.
     *
     * @return la liste de tous les algorithmes de cryptage disponibles
     */
    ArrayList<String> getCryptingAlgorithms();

    /**
     * Cette méthode permets de sélection l'un ou l'autre des algorithmes de cryptage disponibles.
     *
     * @param selected l'algorithme de cryptage sélectionné
     */
    void setSelectedCryptingAlgorithm( String selected );

    /**
     * Cette méthode permets de savoir quel est l'algorithme de cryptage sélectionné.
     *
     * @return l'algorithme de cryptage sélectionné
     */
    String getSelectedCryptingAlgorithm();
}
