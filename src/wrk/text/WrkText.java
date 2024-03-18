package wrk.text;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une
 * caisse à outil dévelopeur de quelques-unes des fonctionnalités les plus
 * usitées avec les fichiers..
 *
 * Workeur spécialisé dans la lecture et écriture de données dans un fichier
 * texte.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class WrkText implements IWrkText {

    /**
     * Le constructeur de la classe.
     */
    public WrkText() {
    }

    /**
     * Cette méthode permets de lire le contenu d'un fichier texte.
     *
     * @param filepath le chemin complet vers le fichier texte à lire
     * @param format le format du fichier texte (UTF8, ISO-8859-1, ..)
     * @return la liste des lignes contenues dans le fichier texte ou null en
     * cas d'erreur
     */
    @Override
    public ArrayList<String> readTextFile(String filepath, Charset format) {

        //
        // VOTRE CODE ICI...
        //
        return null;
    }

    /**
     * Cette méthode permets d'écrire le contenu d'un fichier texte.
     *
     * @param filepath le chemin complet vers le fichier texte à écrire
     * @param format le format du fichier texte (UTF8, ISO-8859-1, ..)
     * @param contenu la liste des lignes à écrire dans le fichier texte
     * @return vrai si et seulement si tout s'est bien passé avec l'écriture des
     * données
     */
    @Override
    public boolean writeTextFile(String filepath, Charset format, ArrayList<String> contenu) {

        //
        // VOTRE CODE ICI...
        //
        return false;
    }
}
