package wrk.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Workeur spécialisé dans le cryptage de données à l'aide de l'algorithme DES.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class WrkCryptoDES extends WrkCrypto {

    /**
     * La clé utilisée par l'algorithme de cryptage mis en oeuvre par cette classe.
     */
    private final static String key = "logusJavaArchiCool"; // Au moins 8 caractères pour DES

    /**
     * L'extension de fichier correspondant à l'algorithme de cryptage mis en oeuvre par cette classe.
     */
    private final static String ENCRYPTED_FILE_EXTENSION = ".des-encrypted";

    /**
     * Le nom correspondant à l'algorithme de cryptage mis en oeuvre par cette classe.
     */
    private final static String ENCRYPTION_ALGORITHM_NAME = "DES";

    /**
     * Le constructeur de la classe.
     */
    public WrkCryptoDES() {
        super( ENCRYPTION_ALGORITHM_NAME );
    }

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier encrypté (il y a plusieurs
     * sortes de cryptage proposés, XOR, DES, ..).
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
     * @param source      le chemin complet vers le fichier a crypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu crypté
     * @return vrai si et seulement si tout s'est bien passé avec le cryptage et écriture des données
     */
    @Override
    public boolean encryptFile( String source, String destination ) {
        boolean resultat = false;

        try {
            InputStream is = new FileInputStream( source );
            OutputStream os = new FileOutputStream( destination );
            resultat = encryptOrDecrypt( key, Cipher.ENCRYPT_MODE, is, os );
        }
        catch ( Exception ex ) {
        }

        return resultat;
    }

    /**
     * Cette méthode permets de décrypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a décrypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décrypté
     * @return vrai si et seulement si tout s'est bien passé avec le décryptage et écriture des données
     */
    @Override
    public boolean decryptFile( String source, String destination ) {

        boolean resultat = false;

        try {
            InputStream is = new FileInputStream( source );
            OutputStream os = new FileOutputStream( destination );
            resultat = encryptOrDecrypt( key, Cipher.DECRYPT_MODE, is, os );
        }
        catch ( Exception ex ) {
        }

        return resultat;
    }

    /**
     * Méthode interne permettant de crypter ou décrypter un fichier dans un autre.
     *
     * @param key  la clé de cryptage/décryptage à utiliser
     * @param mode Cipher.ENCRYPT_MODE ou mode == Cipher.DECRYPT_MODE en fonction de l'action souhaitée
     * @param is   le flux de données devant être crypté
     * @param os   le flux de données dans lequel écrire les données cryptées
     * @return vrai si et seulement si tout s'est parfaitement bien passé
     */
    private boolean encryptOrDecrypt( String key, int mode, InputStream is, OutputStream os ) {

        boolean resultat = false;

        try {
            DESKeySpec dks = new DESKeySpec( key.getBytes() );
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "DES" );
            SecretKey desKey = skf.generateSecret( dks );
            Cipher cipher = Cipher.getInstance( "DES" ); // Voir la Javadoc de Cipher pour les autres algorithmes de cryptage disponibles

            if ( mode == Cipher.ENCRYPT_MODE ) {
                cipher.init( Cipher.ENCRYPT_MODE, desKey );
                CipherInputStream cis = new CipherInputStream( is, cipher );
                doCopy( cis, os );
                resultat = true;
            } else if ( mode == Cipher.DECRYPT_MODE ) {
                cipher.init( Cipher.DECRYPT_MODE, desKey );
                CipherOutputStream cos = new CipherOutputStream( os, cipher );
                doCopy( is, cos );
                resultat = true;
            }
        }
        catch ( Exception e ) {
        }

        return resultat;
    }

    /**
     * Cette méthode permets de copier l'intégralité d'un flux dans un autre.
     *
     * @param is le flux de données dans lequel lire toutes les données
     * @param os le flux de données dans lequel écrire toutes ces données lues
     * @throws IOException pour tout type de problème rencontré lors de la lecture, écriture, fermeture des flux
     *                     concernés
     */
    private void doCopy( InputStream is, OutputStream os ) throws IOException {
        byte[] bytes = new byte[ 4096 ];
        int numBytes;
        while ( ( numBytes = is.read( bytes ) ) != -1 ) {
            os.write( bytes, 0, numBytes );
        }
        os.flush();
        os.close();
        is.close();
    }
}
