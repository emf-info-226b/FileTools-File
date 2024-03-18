package wrk;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Interface MVC2 "Workeur pour le contrôleur".
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public interface IWrkCtrl {

    /**
     * Cette méthode permets de lire le contenu d'un fichier texte.
     *
     * @param filepath le chemin complet vers le fichier texte à lire
     * @param format   le format du fichier texte (UTF8, ISO-8859-1, ..)
     * @return la liste des lignes contenues dans le fichier texte ou null en cas d'erreur
     */
    ArrayList<String> readTextFile( String filepath, Charset format );

    /**
     * Cette méthode permets d'écrire le contenu d'un fichier texte.
     *
     * @param filepath le chemin complet vers le fichier texte à écrire
     * @param format   le format du fichier texte (UTF8, ISO-8859-1, ..)
     * @param contenu  la liste des lignes à écrire dans le fichier texte
     * @return vrai si et seulement si tout s'est bien passé avec l'écriture des données
     */
    boolean writeTextFile( String filepath, Charset format, ArrayList<String> contenu );

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nombre d'algorithmes de compression disponibles.
     *
     * @return le nombre d'algorithmes de compression disponibles
     */
    int getCompressionAlgorithmCount();

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nom de l'un des algorithmes de compression
     * disponibles.
     *
     * @param index l'index de l'algorithme de compression dont on souhaite connaître le nom
     * @return son nom
     */
    String getCompressionAlgorithmName( int index );

    /**
     * Cette méthode permets de compresser un fichier.
     *
     * @param source      le chemin complet vers le fichier a compresser
     * @param destination le chemin complet vers le fichier qui contiendra le contenu compressé
     * @param algorithm l'index de l'algorithme de compression à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec la compression et écriture des données
     */
    boolean compressFile( String source, String destination, int algorithm );

    /**
     * Cette méthode permets de décompresser un fichier.
     *
     * @param source      le chemin complet vers le fichier a décompresser
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décompressé
     * @param algorithm l'index de l'algorithme de compression à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec la décompression et écriture des données
     */
    boolean decompressFile( String source, String destination, int algorithm );

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nombre d'algorithmes de cryptage disponibles.
     *
     * @return le nombre d'algorithmes de cryptage disponibles
     */
    int getEncryptionAlgorithmCount();

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nom de l'un des algorithmes de cryptage
     * disponibles.
     *
     * @param index l'index de l'algorithme de cryptage dont on souhaite connaître le nom
     * @return son nom
     */
    String getEncryptionAlgorithmName( int index );

    /**
     * Cette méthode permets de crypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a crypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu crypté
     * @param algorithm l'index de l'algorithme de cryptage à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec le cryptage et écriture des données
     */
    boolean encryptFile( String source, String destination, int algorithm );

    /**
     * Cette méthode permets de décrypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a décrypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décrypté
     * @param algorithm l'index de l'algorithme de cryptage à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec le décryptage et écriture des données
     */
    boolean decryptFile( String source, String destination, int algorithm );

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier compressé (il y a plusieurs
     * sortes de compressions proposées, GZ, ZIP, ..).
     *
     * @param index l'index de l'algorithme à utiliser
     * @return l'extension de fichier à utiliser
     */
    String getCompressedFileExtension(int index);

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier encrypté (il y a plusieurs
     * sortes de cryptage proposés, XOR, DES, ..).
     *
     * @param index l'index de l'algorithme à utiliser
     * @return l'extension de fichier à utiliser
     */
    String getEncryptedFileExtension(int index);
}
