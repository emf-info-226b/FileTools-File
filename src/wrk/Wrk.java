package wrk;

import ctrl.ICtrlWrk;
import java.nio.charset.Charset;
import java.util.ArrayList;
import wrk.compress.IWrkCompress;
import wrk.compress.WrkCompressGz;
import wrk.compress.WrkCompressZip;
import wrk.crypto.IWrkCrypto;
import wrk.crypto.WrkCryptoDES;
import wrk.crypto.WrkCryptoXOR;
import wrk.text.IWrkText;
import wrk.text.WrkText;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Workeur principal de l'application MVC2.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class Wrk implements IWrkCtrl {

    /**
     * Constructeur de la classe Wrk.
     */
    public Wrk() {
        //
        // Il y a plusieurs implémentations différentes à disposition pour la compression. On va toutes les rajouter.
        //
        iWrkCompressList = new ArrayList<>();
        iWrkCompressList.add( new WrkCompressGz() );
        iWrkCompressList.add( new WrkCompressZip() );

        //
        // Il y a plusieurs implémentations différentes à disposition pour le cryptage. On va toutes les rajouter.
        //
        iWrkCryptoList = new ArrayList<>();
        iWrkCryptoList.add( new WrkCryptoXOR() );
        iWrkCryptoList.add( new WrkCryptoDES() );    // Il y a deux implémentations différentes à disposition. DES est beaucoup plus sûr !

        //
        // Il n'y a qu'une implémentation pour la lecture / écriture des fichiers texte.
        //
        iWrkText = new WrkText();
    }
    ///////////////////////////////////////////////////////////////////////////
    // Méthodes implémentant l'interface IWrkCtrl
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier encrypté (il y a plusieurs
     * sortes de cryptage proposés, XOR, DES, ..).
     *
     * @param index l'index de l'algorithme à utiliser
     * @return l'extension de fichier à utiliser
     */

    @Override
    public String getEncryptedFileExtension( int index ) {
        return iWrkCryptoList.get( index ).getFileExtension();
    }

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter un fichier compressé (il y a plusieurs
     * sortes de compressions proposées, GZ, ZIP, ..).
     *
     * @param index l'index de l'algorithme à utiliser
     * @return l'extension de fichier à utiliser
     */
    @Override
    public String getCompressedFileExtension( int index ) {
        return iWrkCompressList.get( index ).getFileExtension();
    }

    /**
     * Cette méthode permets de lire le contenu d'un fichier texte.
     *
     * @param filepath le chemin complet vers le fichier texte à lire
     * @param format   le format du fichier texte (UTF8, ISO-8859-1, ..)
     * @return la liste des lignes contenues dans le fichier texte ou null en cas d'erreur
     */
    @Override
    public ArrayList<String> readTextFile( String filepath, Charset format ) {
        return iWrkText.readTextFile( filepath, format );
    }

    /**
     * Cette méthode permets d'écrire le contenu d'un fichier texte.
     *
     * @param filepath le chemin complet vers le fichier texte à écrire
     * @param format   le format du fichier texte (UTF8, ISO-8859-1, ..)
     * @param contenu  la liste des lignes à écrire dans le fichier texte
     * @return vrai si et seulement si tout s'est bien passé avec l'écriture des données
     */
    @Override
    public boolean writeTextFile( String filepath, Charset format, ArrayList<String> contenu ) {
        return iWrkText.writeTextFile( filepath, format, contenu );
    }

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nombre d'algorithmes de compression disponibles.
     *
     * @return le nombre d'algorithmes de compression disponibles
     */
    @Override
    public int getCompressionAlgorithmCount() {
        return iWrkCompressList.size();
    }

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nom de l'un des algorithmes de compression
     * disponibles.
     *
     * @param index l'index de l'algorithme de compression dont on souhaite connaître le nom
     * @return son nom
     */
    @Override
    public String getCompressionAlgorithmName( int index ) {
        return iWrkCompressList.get( index ).getAlgorithmName();
    }

    /**
     * Cette méthode permets de compresser un fichier.
     *
     * @param source      le chemin complet vers le fichier a compresser
     * @param destination le chemin complet vers le fichier qui contiendra le contenu compressé
     * @param algorithm l'index de l'algorithme de compression à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec la compression et écriture des données
     */
    @Override
    public boolean compressFile( String source, String destination, int algorithm ) {
        return iWrkCompressList.get( algorithm ).compressFile( source, destination );
    }

    /**
     * Cette méthode permets de décompresser un fichier.
     *
     * @param source      le chemin complet vers le fichier a décompresser
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décompressé
     * @param algorithm l'index de l'algorithme de compression à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec la décompression et écriture des données
     */
    @Override
    public boolean decompressFile( String source, String destination, int algorithm ) {
        return iWrkCompressList.get( algorithm ).decompressFile( source, destination );
    }

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nombre d'algorithmes de cryptage disponibles.
     *
     * @return le nombre d'algorithmes de cryptage disponibles
     */
    @Override
    public int getEncryptionAlgorithmCount() {
        return iWrkCryptoList.size();
    }

    /**
     * Comme il peut y en avoir plusieurs, cette méthode retourne le nom de l'un des algorithmes de cryptage
     * disponibles.
     *
     * @param index l'index de l'algorithme de cryptage dont on souhaite connaître le nom
     * @return son nom
     */
   @Override
    public String getEncryptionAlgorithmName( int index ) {
        return iWrkCryptoList.get( index ).getAlgorithmName();
    }    
    
    /**
     * Cette méthode permets de crypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a crypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu crypté
     * @param algorithm l'index de l'algorithme de cryptage à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec le cryptage et écriture des données
     */
    @Override
    public boolean encryptFile( String source, String destination, int algorithm ) {
        return iWrkCryptoList.get( algorithm ).encryptFile( source, destination );
    }

    /**
     * Cette méthode permets de décrypter un fichier.
     *
     * @param source      le chemin complet vers le fichier a décrypter
     * @param destination le chemin complet vers le fichier qui contiendra le contenu décrypté
     * @param algorithm l'index de l'algorithme de cryptage à utiliser
     * @return vrai si et seulement si tout s'est bien passé avec le décryptage et écriture des données
     */
    @Override
    public boolean decryptFile( String source, String destination, int algorithm ) {
        return iWrkCryptoList.get( algorithm ).decryptFile( source, destination );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters et Setters
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Getter de la référence au contrôleur de l'application.
     *
     * @return la référence au contrôleur de l'application
     */
    public ICtrlWrk getRefCtrl() {
        return refCtrl;
    }

    /**
     * Setter de la référence au contrôleur de l'application.
     *
     * @param refCtrl la nouvelle référence au contrôleur de l'application
     */
    public void setRefCtrl( ICtrlWrk refCtrl ) {
        this.refCtrl = refCtrl;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Attributs
    ///////////////////////////////////////////////////////////////////////////

    /**
     * La référence au contrôleur de l'application.
     */
    private ICtrlWrk refCtrl;
    private ArrayList<IWrkCompress> iWrkCompressList;
    private ArrayList<IWrkCrypto> iWrkCryptoList;
    private IWrkText iWrkText;
}
