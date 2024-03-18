package beans;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Classe renfermant les constantes de l'application.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class Constantes {

    /**
     * Constante indiquant le set de caractères à utiliser pour la lecture et l'écriture de texte depuis/dans un
     * fichier. C'est plus propre ainsi que par nom ("UTF8" ou "UTF-8" ou "Utf-8" ??).
     */
    public final static Charset TEXT_FILE_FORMAT = StandardCharsets.UTF_8;

}
