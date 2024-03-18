package wrk.crypto;

/**
 * Application "WrkCrypto".
 * 
 * Commentaire sur la classe.
 * 
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 31 mars 2014
 * @version 0.1
 */
public abstract class WrkCrypto implements IWrkCrypto {

    /**
     * Le nom de l'algorithme de compression implémenté.
     */
    private String algorithmName;

    /**
     * Le constructeur de la classe.
     * @param algorithmName le nom de cet algorithme
     */
    public WrkCrypto( String algorithmName ) {
        this.algorithmName = algorithmName;
    }

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier encrypté (il y a plusieurs
     * sortes de cryptage proposés, XOR, DES, ..).
     *
     * @return l'extension de fichier à utiliser
     */
    @Override
    public abstract String getFileExtension();

    /**
     * Cette méthode retourne le nom de l'algorithme de cryptage utilisé.
     *
     * @return le nom de l'algorithme de cryptage utilisé
     */
    @Override
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Cette méthode permets de crypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a crypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu crypté
     * @return vrai si et seulement si tout s'est bien passé avec le cryptage et écriture des données
     */
    @Override
    public abstract boolean encryptFile( String source, String destination );

    /**
     * Cette méthode permets de décrypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a décrypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décrypté
     * @return vrai si et seulement si tout s'est bien passé avec le décryptage et écriture des données
     */
    @Override
    public abstract boolean decryptFile( String source, String destination );
}
