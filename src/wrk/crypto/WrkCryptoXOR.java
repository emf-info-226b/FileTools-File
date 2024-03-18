package wrk.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une
 * caisse à outil dévelopeur de quelques-unes des fonctionnalités les plus
 * usitées avec les fichiers..
 *
 * Workeur spécialisé dans le cryptage de données à l'aide d'un simple XOR de
 * chaque byte avec une clé.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class WrkCryptoXOR extends WrkCrypto {

    /**
     * La clé utilisée par l'algorithme de cryptage mis en oeuvre par cette
     * classe.
     */
    private final static int key = 137;

    /**
     * L'extension de fichier correspondant à l'algorithme de cryptage mis en
     * oeuvre par cette classe.
     */
    private final static String ENCRYPTED_FILE_EXTENSION = ".xor-encrypted";

    /**
     * Le nom correspondant à l'algorithme de cryptage mis en oeuvre par cette
     * classe.
     */
    private final static String ENCRYPTION_ALGORITHM_NAME = "XOR";

    /**
     * Le constructeur de la classe.
     */
    public WrkCryptoXOR() {
        super(ENCRYPTION_ALGORITHM_NAME);
    }

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter
     * un fichier encrypté (il y a plusieurs sortes de cryptage proposés, XOR,
     * DES, ..).
     *
     * @return l'extension de fichier à utiliser
     */
    @Override
    public String getFileExtension() {
        return ENCRYPTED_FILE_EXTENSION;
    }

    /**
     * Cette méthode permets de crypter un fichier.
     *
     * @param source le chemin complet vers le fichier a crypter
     * @param destination le chemin complet vers le fichier qui contiendra le
     * contenu crypté
     * @return vrai si et seulement si tout s'est bien passé avec le cryptage et
     * écriture des données
     */
    @Override
    public boolean encryptFile(String source, String destination) {
        boolean resultat = false;

        try {
            InputStream is = new FileInputStream(source);
            OutputStream os = new FileOutputStream(destination);
            resultat = encryptOrDecrypt(key, is, os);
        } catch (Exception ex) {
        }

        return resultat;
    }

    /**
     * Cette méthode permets de décrypter un fichier.
     *
     * @param source le chemin complet vers le fichier a décrypter
     * @param destination le chemin complet vers le fichier qui contiendra le
     * contenu décrypté
     * @return vrai si et seulement si tout s'est bien passé avec le décryptage
     * et écriture des données
     */
    @Override
    public boolean decryptFile(String source, String destination) {

        boolean resultat = false;

        try {
            InputStream is = new FileInputStream(source);
            OutputStream os = new FileOutputStream(destination);
            resultat = encryptOrDecrypt(key, is, os);
        } catch (Exception ex) {
        }

        return resultat;
    }

    /**
     * Méthode interne permettant de crypter ou décrypter (l'algorithme utilisé
     * est symétrique) un fichier dans un autre.
     *
     * @param key la clé de cryptage/décryptage à utiliser
     * @param is le flux de données devant être crypté
     * @param os le flux de données dans lequel écrire les données cryptées
     * @return vrai si et seulement si tout s'est parfaitement bien passé
     */
    private boolean encryptOrDecrypt(int key, InputStream is, OutputStream os) {

        //
        // VOTRE CODE ICI...
        //
        return false;

    }
}
