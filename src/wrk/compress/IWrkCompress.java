package wrk.compress;

/**
 * Application "IWrkCompress".
 *
 * Interface pour workeur spécialisé dans la compression de données.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 29 mars 2014
 * @version 0.1
 */
public interface IWrkCompress {

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier compressé (il y a plusieurs
     * sortes de compressions proposées, GZ, ZIP, ..).
     *
     * @return l'extension de fichier à utiliser
     */
    String getFileExtension();

    /**
     * Cette méthode retourne le nom de l'algorithme de compression utilisé.
     *
     * @return le nom de l'algorithme de compression utilisé
     */
    String getAlgorithmName();

    /**
     * Cette méthode permets de compresser un fichier.
     *
     * @param source      le chemin complet vers le fichier a compresser
     * @param destination le chemin complet vers le fichier qui contiendra le contenu compressé
     * @return vrai si et seulement si tout s'est bien passé avec la compression et écriture des données
     */
    boolean compressFile( String source, String destination );

    /**
     * Cette méthode permets de décompresser un fichier.
     *
     * @param source      le chemin complet vers le fichier a décompresser
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décompressé
     * @return vrai si et seulement si tout s'est bien passé avec la décompression et écriture des données
     */
    boolean decompressFile( String source, String destination );
}
