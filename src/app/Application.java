package app;

import ctrl.Ctrl;
import ihm.Ihm;
import wrk.Wrk;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Classe principale permettant de démarrer le programme MVC2.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class Application {

    /**
     * LA méthode main() de l'application, là où tout commence mais... tout se finit-il bien là ?
     *
     * @param args les arguments du programme passés sur la ligne de commande
     */
    public static void main( String[] args ) {

        Ctrl ctrl = new Ctrl();
        Wrk wrk = new Wrk();
        Ihm ihm = new Ihm();
        
        ctrl.setRefIhm(ihm);
        ctrl.setRefWrk(wrk);
        
        wrk.setRefCtrl(ctrl);
        ihm.setRefCtrl(ctrl);
        
        ctrl.start();
    }
}
