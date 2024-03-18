package wrk.crypto;

/**
 * Application "IWrkCrypto".
 *
 * Interface pour workeur spécialisé dans le cryptage de données.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 29 mars 2014
 * @version 0.1
 */
public interface IWrkCrypto {

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier encrypté (il y a plusieurs
     * sortes de cryptage proposés, XOR, DES, ..).
     *
     * @return l'extension de fichier à utiliser
     */
    String getFileExtension();

    /**
     * Cette méthode retourne le nom de l'algorithme de cryptage utilisé.
     *
     * @return le nom de l'algorithme de cryptage utilisé
     */
    String getAlgorithmName();

    /**
     * Cette méthode permets de crypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a crypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu crypté
     * @return vrai si et seulement si tout s'est bien passé avec le cryptage et écriture des données
     */
    boolean encryptFile( String source, String destination );

    /**
     * Cette méthode permets de décrypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a décrypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décrypté
     * @return vrai si et seulement si tout s'est bien passé avec le décryptage et écriture des données
     */
    boolean decryptFile( String source, String destination );
}
