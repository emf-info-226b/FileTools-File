package ctrl;

import beans.Constantes;
import ihm.IIhmCtrl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import wrk.IWrkCtrl;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Contrôleur principal de l'application MVC2.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class Ctrl implements ICtrlIhm, ICtrlWrk {

    /**
     * Le constructeur de la classe Ctrl.
     */
    public Ctrl() {
        refWrk = null;
        refIhm = null;
        txtFilePath = null;
    }

    /**
     * Méthode de démarrage de l'application MVC2.
     */
    public void start() {
        // Algorithmes à utiliser au démarrage
        currentCompressionAlgorithmName = getRefWrk().getCompressionAlgorithmName( 0 );
        currentCryptingAlgorithmName = getRefWrk().getEncryptionAlgorithmName( 0 );

        // Démarrer l'ihm
        getRefIhm().ihmStart();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Méthodes utilitaires internes au contrôleur
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Sur la base d'un chemin de fichier complet vers un fichier non compressé, cette méthode retourne le chemin
     * complet du même fichier une fois compressé.
     *
     * @param uncompressedFilePath chemin de fichier complet vers le fichier qui devra être compressé
     * @return le chemin complet du fichier compressé
     */
    private String computeCompressedFilePath( String uncompressedFilePath ) {
        // Ici on ne complique pas, on rajoute l'extension à la fin.
        // Du coup, [blabla.txt] deviendra [blabla.txt.gz].
        return uncompressedFilePath + getRefWrk().getCompressedFileExtension( getSelectedCompressionAlgorithmIndex() );
    }

    /**
     * Sur la base d'un chemin de fichier complet vers un fichier compressé, cette méthode retourne le chemin complet du
     * même fichier une fois décompressé.
     *
     * @param compressedFilePath chemin de fichier complet vers le fichier qui devra être décompressé
     * @return le chemin complet du fichier décompressé
     */
    private String computeUncompressedFilePath( String compressedFilePath ) {
        String resultat = null;

        // On supprime l'extension uniquement si on la trouve.
        if ( !compressedFilePath.endsWith( getRefWrk().getCompressedFileExtension( getSelectedCompressionAlgorithmIndex() ) ) ) {
            getRefIhm().messageErreur( "Le fichier [" + compressedFilePath + "] ne se termine pas avec l'extension [" + getRefWrk().getCompressedFileExtension( getSelectedCompressionAlgorithmIndex() ) + "]." );
        } else {
            int pos = compressedFilePath.lastIndexOf( getRefWrk().getCompressedFileExtension( getSelectedCompressionAlgorithmIndex() ) );
            resultat = compressedFilePath.substring( 0, pos );
        }

        return resultat;
    }

    /**
     * Sur la base d'un chemin de fichier complet vers un fichier non crypté, cette méthode retourne le chemin complet
     * du même fichier une fois crypté.
     *
     * @param uncryptedFilePath chemin de fichier complet vers le fichier qui devra être crypté
     * @return le chemin complet du fichier crypté
     */
    private String computeCryptedFilePath( String uncryptedFilePath ) {
        // Ici on ne complique pas, on rajoute l'extension à la fin.
        // Du coup, [blabla.txt] deviendra [blabla.txt.xor-crypted] (si c'est l'algorithme XOR qui est utilisé).
        return uncryptedFilePath + getRefWrk().getEncryptedFileExtension( getSelectedCryptingAlgorithmIndex() );
    }

    /**
     * Sur la base d'un chemin de fichier complet vers un fichier crypté, cette méthode retourne le chemin complet du
     * même fichier une fois décrypté.
     *
     * @param cryptedFilePath chemin de fichier complet vers le fichier qui devra être décrypté
     * @return le chemin complet du fichier décrypté
     */
    private String computeDecryptedFilePath( String cryptedFilePath ) {
        String resultat = null;

        // On supprime l'extension uniquement si on la trouve
        if ( !cryptedFilePath.endsWith( getRefWrk().getEncryptedFileExtension( getSelectedCryptingAlgorithmIndex() ) ) ) {
            getRefIhm().messageErreur( "Le fichier [" + cryptedFilePath + "] ne se termine pas avec l'extension [" + getRefWrk().getEncryptedFileExtension( getSelectedCryptingAlgorithmIndex() ) + "]." );
        } else {
            int pos = cryptedFilePath.lastIndexOf( getRefWrk().getEncryptedFileExtension( getSelectedCryptingAlgorithmIndex() ) );
            resultat = cryptedFilePath.substring( 0, pos );
        }

        return resultat;
    }

    private int getSelectedCompressionAlgorithmIndex() {
        int resultat = -1;

        for ( int i = 0; i < getRefWrk().getCompressionAlgorithmCount(); i++ ) {
            if ( getRefWrk().getCompressionAlgorithmName( i ).equals( currentCompressionAlgorithmName ) ) {
                resultat = i;
                break;
            }
        }

        return resultat;
    }

    private int getSelectedCryptingAlgorithmIndex() {
        int resultat = -1;

        for ( int i = 0; i < getRefWrk().getEncryptionAlgorithmCount(); i++ ) {
            if ( getRefWrk().getEncryptionAlgorithmName( i ).equals( currentCryptingAlgorithmName ) ) {
                resultat = i;
                break;
            }
        }

        return resultat;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Méthodes implémentant l'interface ICtrlIhm
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'application est en train de se fermer. Cela permet de
     * sauvegarder l'état de l'application.
     */
    @Override
    public void ihmExiting() {
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de sélectionner
     * un fichier texte.
     */
    @Override
    public void SelectFileActionPerformed() {
        String filepath = getRefIhm().askUserToSelectTextFile( txtFilePath );
        if ( filepath != null ) {
            txtFilePath = filepath;
            getRefIhm().setFilePath( filepath );
        }
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de lire le
     * fichier texte.
     */
    @Override
    public void ReadTextFileActionPerformed() {
        ArrayList<String> lines = getRefWrk().readTextFile( txtFilePath, Constantes.TEXT_FILE_FORMAT );
        getRefIhm().setTextContent( lines );
        if ( lines != null ) {
            getRefIhm().messageInformation( "La lecture s'est effectuée correctement !" );
        } else {
            getRefIhm().messageErreur( "La lecture ne s'est pas effectuée correctement !" );
        }
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant d'écrire le
     * fichier texte.
     */
    @Override
    public void WriteTextFileActionPerformed() {
        ArrayList<String> lines = getRefIhm().getTextContent();
        boolean resultat = getRefWrk().writeTextFile( txtFilePath, Constantes.TEXT_FILE_FORMAT, lines );
        if ( resultat ) {
            getRefIhm().messageInformation( "L'écriture s'est effectuée correctement !" );
        } else {
            getRefIhm().messageErreur( "L'écriture ne s'est pas effectuée correctement !" );
        }
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de compresser
     * un fichier.
     */
    @Override
    public void CompressActionPerformed() {
        //
        // Demander à l'utilisateur de sélectionner le fichier à compresser
        //
        String filepath = getRefIhm().askUserToSelectAnyFile( uncompressedFilePath );

        //
        // En a-t-il sélectionné un ?
        //
        if ( filepath != null ) {
            uncompressedFilePath = filepath;

            String source = null;
            String destination = null;
            long sourceSize = -1;
            long destinationSize = -1;

            try {
                //
                // Get initial file size
                //
                File sourceFile = new File( filepath );
                source = sourceFile.getCanonicalPath();
                sourceSize = sourceFile.length();

                //
                // Compute compressed file name
                //
                destination = computeCompressedFilePath( source );

                //
                // Compress
                //
                if ( getRefWrk().compressFile( source, destination, getSelectedCompressionAlgorithmIndex() ) ) {
                    //
                    // Get compressed file size
                    //
                    File destinationFile = new File( destination );
                    destinationSize = destinationFile.length();

                    //
                    // Compute compression ratio
                    //
                    double percentage = ( ( int ) ( 100.0 - ( destinationSize * 100.0 / sourceSize ) ) * 100 ) / 100.0;

                    //
                    // Show to user
                    //
                    getRefIhm().messageInformation( "La compression du fichier [" + source + "] en [" + destination + "] a réussi. Il fait " + percentage + "% de moins que la taille initiale" );

                } else {
                    getRefIhm().messageErreur( "La compression du fichier [" + source + "] a échoué." );
                }
            }
            catch ( IOException ex ) {
            }
        }
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de décompresser
     * un fichier.
     */
    @Override
    public void DecompressActionPerformed() {
        //
        // Demander à l'utilisateur de sélectionner le fichier à décompresser
        //
        String filepath = getRefIhm().askUserToSelectAnyFile( compressedFilePath );

        //
        // En a-t-il sélectionné un ?
        //
        if ( filepath != null ) {
            compressedFilePath = filepath;

            String source = null;
            String destination = null;
            long sourceSize = -1;
            long destinationSize = -1;

            try {
                //
                // Get initial file size
                //
                File sourceFile = new File( filepath );
                source = sourceFile.getCanonicalPath();
                sourceSize = sourceFile.length();

                //
                // Compute compressed file name
                //
                destination = computeUncompressedFilePath( source );

                //
                // Compress
                //
                if ( getRefWrk().decompressFile( source, destination, getSelectedCompressionAlgorithmIndex() ) ) {
                    //
                    // Get compressed file size
                    //
                    File destinationFile = new File( destination );
                    destinationSize = destinationFile.length();

                    //
                    // Compute compression ratio
                    //
                    double percentage = ( ( int ) ( ( destinationSize * 100.0 / sourceSize ) - 100.0 ) * 100 ) / 100.0;

                    //
                    // Show to user
                    //
                    getRefIhm().messageInformation( "La décompression du fichier [" + source + "] en [" + destination + "] a réussi. Il fait " + percentage + "% de plus que de la taille initiale." );
                } else {
                    getRefIhm().messageErreur( "La décompression du fichier [" + source + "] a échoué." );
                }
            }
            catch ( IOException ex ) {
            }
        }
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de crypter un
     * fichier.
     */
    @Override
    public void EncryptActionPerformed() {
        //
        // Demander à l'utilisateur de sélectionner le fichier à compresser
        //
        String filepath = getRefIhm().askUserToSelectAnyFile( uncryptedFilePath );

        //
        // En a-t-il sélectionné un ?
        //
        if ( filepath != null ) {
            uncryptedFilePath = filepath;

            try {
                //
                // Get initial file size
                //
                File sourceFile = new File( filepath );
                String source = sourceFile.getCanonicalPath();

                //
                // Compute compressed file name
                //
                String destination = computeCryptedFilePath( source );

                //
                // Encrypt
                //
                if ( getRefWrk().encryptFile( source, destination, getSelectedCryptingAlgorithmIndex() ) ) {
                    getRefIhm().messageInformation( "L'encryptage du fichier [" + source + "] en [" + destination + "] a réussi." );

                } else {
                    getRefIhm().messageErreur( "L'encryptage du fichier [" + source + "] a échoué." );
                }
            }
            catch ( IOException ex ) {
            }
        }
    }

    /**
     * Cette méthode est appelée par l'ihm pour indiquer que l'utilisateur a pressé le bouton permettant de décrypter un
     * fichier.
     */
    @Override
    public void DecryptActionPerformed() {
        //
        // Demander à l'utilisateur de sélectionner le fichier à compresser
        //
        String filepath = getRefIhm().askUserToSelectAnyFile( cryptedFilePath );

        //
        // En a-t-il sélectionné un ?
        //
        if ( filepath != null ) {
            cryptedFilePath = filepath;

            try {
                //
                // Get initial file size
                //
                File sourceFile = new File( filepath );
                String source = sourceFile.getCanonicalPath();

                //
                // Compute compressed file name
                //
                String destination = computeDecryptedFilePath( source );

                //
                // Decrypt
                //
                if ( getRefWrk().decryptFile( source, destination, getSelectedCryptingAlgorithmIndex() ) ) {
                    getRefIhm().messageInformation( "Le décryptage du fichier [" + source + "] en [" + destination + "] a réussi." );

                } else {
                    getRefIhm().messageErreur( "Le décryptage du fichier [" + source + "] a échoué." );
                }
            }
            catch ( IOException ex ) {
            }
        }
    }

    /**
     * Cette méthode retourne la liste de tous les algorithmes de compression disponibles.
     *
     * @return la liste de tous les algorithmes de compression disponibles
     */
    @Override
    public ArrayList<String> getCompressionAlgorithms() {
        ArrayList<String> resultat = new ArrayList<>();

        for ( int i = 0; i < getRefWrk().getCompressionAlgorithmCount(); i++ ) {
            String algorithmName = getRefWrk().getCompressionAlgorithmName( i );
            resultat.add( algorithmName );
        }

        return resultat;
    }

    /**
     * Cette méthode permets de sélection l'un ou l'autre des algorithmes de compression disponibles.
     *
     * @param selected l'algorithme de compression sélectionné
     */
    @Override
    public void setSelectedCompressionAlgorithm( String selected ) {
        currentCompressionAlgorithmName = selected;
    }

    /**
     * Cette méthode permets de savoir quel est l'algorithme de compression sélectionné.
     *
     * @return l'algorithme de compression sélectionné
     */
    @Override
    public String getSelectedCompressionAlgorithm() {
        return currentCompressionAlgorithmName;
    }

    /**
     * Cette méthode retourne la liste de tous les algorithmes de cryptage disponibles.
     *
     * @return la liste de tous les algorithmes de cryptage disponibles
     */
    @Override
    public ArrayList<String> getCryptingAlgorithms() {
        ArrayList<String> resultat = new ArrayList<>();

        for ( int i = 0; i < getRefWrk().getEncryptionAlgorithmCount(); i++ ) {
            String algorithmName = getRefWrk().getEncryptionAlgorithmName( i );
            resultat.add( algorithmName );
        }

        return resultat;
    }

    /**
     * Cette méthode permets de sélection l'un ou l'autre des algorithmes de cryptage disponibles.
     *
     * @param selected l'algorithme de cryptage sélectionné
     */
    @Override
    public void setSelectedCryptingAlgorithm( String selected ) {
        currentCryptingAlgorithmName = selected;
    }

    /**
     * Cette méthode permets de savoir quel est l'algorithme de cryptage sélectionné.
     *
     * @return l'algorithme de cryptage sélectionné
     */
    @Override
    public String getSelectedCryptingAlgorithm() {
        return currentCryptingAlgorithmName;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters et Setters
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Getter de la référence au Workeur de l'application.
     *
     * @return la référence au Workeur de l'application
     */
    public IWrkCtrl getRefWrk() {
        return refWrk;
    }

    /**
     * Setter de la référence au Workeur de l'application.
     *
     * @param wrk la référence au Workeur de l'application
     */
    public void setRefWrk( IWrkCtrl wrk ) {
        this.refWrk = wrk;
    }

    /**
     * Getter de la référence à l'Ihm de l'application.
     *
     * @return la référence à l'Ihm de l'application
     */
    public IIhmCtrl getRefIhm() {
        return refIhm;
    }

    /**
     * Setter de la référence à l'ihm de l'application.
     *
     * @param ihm la référence à l'ihm de l'application
     */
    public void setRefIhm( IIhmCtrl ihm ) {
        this.refIhm = ihm;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Attributs de la classe
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Référence au Workeur principal de l'application.
     */
    private IWrkCtrl refWrk;
    /**
     * Référence à l'Ihm principale de l'application.
     */
    private IIhmCtrl refIhm;

    /**
     * Les derniers chemin de fichier utilisés par l'application.
     */
    private String txtFilePath;
    private String uncompressedFilePath;
    private String compressedFilePath;
    private String uncryptedFilePath;
    private String cryptedFilePath;

    /**
     * Le nom des algorithmes actuelles sélectionnés.
     */
    private String currentCompressionAlgorithmName;
    private String currentCryptingAlgorithmName;
}
