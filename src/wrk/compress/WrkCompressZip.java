package wrk.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une
 * caisse à outil dévelopeur de quelques-unes des fonctionnalités les plus
 * usitées avec les fichiers..
 *
 * Workeur spécialisé dans la compression de données au format ZIP.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class WrkCompressZip extends WrkCompress {

    /**
     * L'extension de fichier correspondant à l'algorithme de compression mis en
     * oeuvre par cette classe.
     */
    private final static String COMPRESSED_FILE_EXTENSION = ".zip";

    /**
     * Le nom correspondant à l'algorithme de compression mis en oeuvre par
     * cette classe.
     */
    private final static String COMPRESSION_ALGORITHM_NAME = "ZIP";

    /**
     * Le constructeur de la classe.
     */
    public WrkCompressZip() {
        super(COMPRESSION_ALGORITHM_NAME);
    }

    /**
     * Cette méthode retourne l'extension de fichier à utiliser pour représenter
     * un fichier compressé (il y a plusieurs sortes de compressions proposées,
     * GZ, ZIP, ..).
     *
     * @return l'extension de fichier à utiliser
     */
    @Override
    public String getFileExtension() {
        return COMPRESSED_FILE_EXTENSION;
    }

    /**
     * Cette méthode permets de compresser un fichier.
     *
     * @param source le chemin complet vers le fichier a compresser
     * @param destination le chemin complet vers le fichier qui contiendra le
     * contenu compressé
     * @return vrai si et seulement si tout s'est bien passé avec la compression
     * et écriture des données
     */
    @Override
    public boolean compressFile(String source, String destination) {

        //
        // VOTRE CODE ICI...
        //
        return false;
    }

    /**
     * Cette méthode permets de décompresser un fichier.
     *
     * @param source le chemin complet vers le fichier a décompresser
     * @param destination le chemin complet vers le fichier qui contiendra le
     * contenu décompressé
     * @return vrai si et seulement si tout s'est bien passé avec la
     * décompression et écriture des données
     */
    @Override
    public boolean decompressFile(String source, String destination) {

        //
        // VOTRE CODE ICI...
        //
        return false;
    }

}
