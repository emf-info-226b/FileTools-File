package ihm;

import ctrl.ICtrlIhm;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Application "FileTools". Le projet FileTools a pour but de constituer une caisse à outil dévelopeur de quelques-unes
 * des fonctionnalités les plus usitées avec les fichiers..
 *
 * Ihm principale de l'application MVC2.
 *
 * @author <a href="mailto:friedlip@edufr.ch">Paolo Friedli</a>
 * @since 24.03.2014
 * @version 1.0.0
 */
public class Ihm extends javax.swing.JFrame implements IIhmCtrl {

    /**
     * Constructeur de la classe Ihm. Ce constructeur crée l'ihm, l'initialise correctement et mais ne l'affiche pas
     * encore.
     */
    public Ihm() {
        // Dire à Java de faire ressembler cette application à celles de 
        // la plateforme sur laquelle il tourne.
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception e ) {
        }

        // Initialiser les composants de notre ihm.
        initComponents();

        // Notre fenêtre peut être redimensionnée, mais pas plus petit que..
        setMinimumSize( new Dimension( 800, 740 ) );

        // Pour donner un icône à notre application
        try {
            // Définir l'icône de l'application.
            setIconImage( new ImageIcon( getClass().getResource( "resources/appIcon.png" ) ).getImage() );
            // Et sur Mac, l'icône utilisée dans le Dock
            //com.apple.eawt.Application.getApplication().setDockIconImage( new ImageIcon(getClass().getResource( "resources/appIcon.png" )).getImage());
        }
        catch ( Exception e ) {
        }

        // Définir le titre de l'application.
        setTitle( "FileTools v1.0.0 - written by Paolo Friedli" );

        // Ne pas fermer la fenêtre automatiquement, nous appellerons dispose() nous-même.
        setDefaultCloseOperation( javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE );

        // Laisser la plateforme sur laquelle Java tourne placer nativement 
        // la fenêtre.
        setLocationByPlatform( true );

        // S'assurer qu'on soit informé lorsque l'utilisateur voudra quitter 
        // cette fenêtre.
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent we ) {
                // Signalons au contrôleur que l'utilisateur veut quitter.
                ihmExiting();
            }
        } );

        // Tranformer les JLabels souhaités en "liens cliquables"
        rendreJLabelNaviguable( jLabelIconWebSiteLink, "https://www.emf.ch/", "http://www.emf.ch/" );

    }

    ///////////////////////////////////////////////////////////////////////////
    // Méthodes internes à l'ihm (privées)
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Cette méthode permets de transformer un JLabel en un "lien html" cliquable.
     *
     * @param label      le JLabel concerné par cette transformation
     * @param url        le lien vers lequel on souhaite naviguer en cas de clic
     * @param urlVisible le lien visible pour l'utilisateur
     */
    private void rendreJLabelNaviguable( JLabel label, final String url, String urlVisible ) {
        label.setText( "<html><a href=\"\">" + urlVisible + "</a></html>" );
        label.setCursor( new Cursor( Cursor.HAND_CURSOR ) );
        label.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                try {
                    Desktop.getDesktop().browse( new URI( url ) );
                }
                catch ( Exception ex ) {
                    // Houston, il y a un problème avec le lien...
                }
            }
        } );
    }

    /**
     * Cette méthode est appelée pour indiquer que l'application est en train de se fermer. Cela permet d'informer le
     * contrôleur de cet état de fait.
     */
    private void ihmExiting() {
        Icon icon = new ImageIcon( Ihm.class.getResource( "resources/question-64.png" ) );
        if ( JOptionPane.showConfirmDialog( getRootPane(), "Voulez-vous vraiment quitter ?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon ) == JOptionPane.YES_OPTION ) {
            // Informer le contrôleur qu'on veut quitter
            getRefCtrl().ihmExiting();

            // Arrêter ce qui doit être arrêté côté Ihm (timer, threads, ..)
            // rien à faire...
            // Fermer l'Ihm
            dispose();
        }
    }

    /**
     * Cette méthode permets de demander à l'utilisateur de sélectionner un fichier.
     *
     * @param initialFileName le chemin de fichier vers un précédent fichier ou null si aucun
     * @param dialogTitle     le titre de la boîte de dialogue qui sera affichée
     * @param filedescription la description textuelle du format de fichier que l'utilisateur va devoir sélectionner
     * @param fileExtensions  la liste des extensions tolérées
     * @return le chemin de fichier complet du fichier sélectionné ou null en cas d'annullation ou erreur
     */
    private String askUserToSelectFileType( String initialFileName, String dialogTitle, String filedescription, String... fileExtensions ) {
        String result = null;

        JFileChooser fc = new JFileChooser( initialFileName );
        if ( filedescription != null ) {
            fc.setFileFilter( new FileNameExtensionFilter( filedescription, fileExtensions ) );
        }
        fc.setDialogTitle( dialogTitle );
        fc.setDialogType( JFileChooser.OPEN_DIALOG );
        fc.setFileHidingEnabled( true );
        fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        fc.setMultiSelectionEnabled( false );
        try {
            fc.setSelectedFile( new File( initialFileName ) );
        }
        catch ( Exception e ) {
        }
        if ( fc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
            result = fc.getSelectedFile().getPath();
        }

        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Méthodes implémentant l'interface IIhmCtrl
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Cette méthode est appelée par le contrôleur de l'application pour démarrer l'ihm. En l'occurrence ici on ne fait
     * que de rendre visible notre fenêtre.
     */
    @Override
    public void ihmStart() {
        // Afficher l'ihm
        setVisible( true );

        // Remplir la liste des algorihtmes de compression
        jComboBoxCompressionAlgorithm.setModel( new ComboBoxModel() {

            @Override
            public void setSelectedItem( Object anItem ) {
                getRefCtrl().setSelectedCompressionAlgorithm( ( String ) anItem );
            }

            @Override
            public Object getSelectedItem() {
                return getRefCtrl().getSelectedCompressionAlgorithm();
            }

            @Override
            public int getSize() {
                return getRefCtrl().getCompressionAlgorithms().size();
            }

            @Override
            public Object getElementAt( int index ) {
                return getRefCtrl().getCompressionAlgorithms().get( index );
            }

            @Override
            public void addListDataListener( ListDataListener l ) {
            }

            @Override
            public void removeListDataListener( ListDataListener l ) {
            }
        } );

        // Remplir la liste des algorihtmes de compression
        jComboBoxEncryptionAlgorithm.setModel( new ComboBoxModel() {

            @Override
            public void setSelectedItem( Object anItem ) {
                getRefCtrl().setSelectedCryptingAlgorithm( ( String ) anItem );
            }

            @Override
            public Object getSelectedItem() {
                return getRefCtrl().getSelectedCryptingAlgorithm();
            }

            @Override
            public int getSize() {
                return getRefCtrl().getCryptingAlgorithms().size();
            }

            @Override
            public Object getElementAt( int index ) {
                return getRefCtrl().getCryptingAlgorithms().get( index );
            }

            @Override
            public void addListDataListener( ListDataListener l ) {
            }

            @Override
            public void removeListDataListener( ListDataListener l ) {
            }
        } );
    }

    /**
     * Cette méthode permet d'afficher le chemin du fichier texte sélectionné par l'utilisateur.
     *
     * @param filepath le chemin du fichier sélectionné à afficher
     */
    @Override
    public void setFilePath( String filepath ) {
        boolean bActivateLoad = false;
        boolean bActivateSave = false;

        jTextFieldFilePath.setText( filepath );

        try {
            bActivateSave = ( filepath != null ) && !filepath.isEmpty();
            bActivateLoad = ( filepath != null ) && ( new File( filepath ).canRead() );
        }
        catch ( Exception e ) {
        }

        jButtonReadTextFile.setEnabled( bActivateLoad );
        jButtonWriteTextFile.setEnabled( bActivateSave );
    }

    /**
     * Cette méthode permet d'afficher le contenu du fichier texte.
     *
     * @param content la liste des lignes contenues dans le fichier texte
     */
    @Override
    public void setTextContent( ArrayList<String> content ) {
        String textContent = "";

        if ( content != null ) {
            for ( String line : content ) {
                if ( !textContent.isEmpty() ) {
                    textContent += "\r\n";
                }
                textContent += line;
            }
        }

        jTextPaneFileContent.setText( textContent );
    }

    /**
     * Cette méthode retourne la liste des lignes contenues dans le fichier texte.
     *
     * @return la liste des lignes contenues dans le fichier texte
     */
    @Override
    public ArrayList<String> getTextContent() {
        ArrayList<String> resultat = null;

        String content = jTextPaneFileContent.getText();
        if ( content != null ) {
            resultat = new ArrayList<String>();
            String[] lines = content.split( "\r\n" );
            for ( int i = 0; i < lines.length; i++ ) {
                resultat.add( lines[i] );
            }
        }

        return resultat;
    }

    /**
     * Cette méthode permet d'afficher un message d'information à l'utilisateur.
     *
     * @param message le message d'information à afficher
     */
    @Override
    public void messageInformation( String message ) {
        Icon icon = new ImageIcon( Ihm.class.getResource( "resources/ok-64.png" ) );
        JOptionPane.showMessageDialog( this, message, "Information", JOptionPane.INFORMATION_MESSAGE, icon );
    }

    /**
     * Cette méthode permet d'afficher un message d'erreur à l'utilisateur.
     *
     * @param message le message d'erreur à afficher
     */
    @Override
    public void messageErreur( String message ) {
        Icon icon = new ImageIcon( Ihm.class.getResource( "resources/error-64.png" ) );
        JOptionPane.showMessageDialog( this, message, "Erreur", JOptionPane.ERROR_MESSAGE, icon );
    }

    /**
     * Cette méthode permet de demander à l'utilisateur de sélectionner un fichier texte.
     *
     * @param initialFileName le chemin de fichier vers un précédent fichier texte qui avait été sélectionné ou null
     *                        s'il n'y en a pas
     * @return le chemin de fichier vers ule fichier texte sélectionné par l'utilisateur ou null si annullé ou si
     *         problème
     */
    @Override
    public String askUserToSelectTextFile( String initialFileName ) {
        return askUserToSelectFileType( initialFileName, "Sélectionnez le fichier texte svp :", "Fichiers Texte (*.txt, *.TXT)", "txt", "TXT" );
    }

    /**
     * Cette méthode permet de demander à l'utilisateur de sélectionner un fichier quelconque.
     *
     * @param initialFileName le chemin de fichier vers un précédent fichier quelconque qui avait été sélectionné ou
     *                        null s'il n'y en a pas
     * @return le chemin de fichier vers ule fichier quelconque sélectionné par l'utilisateur ou null si annullé ou si
     *         problème
     */
    @Override
    public String askUserToSelectAnyFile( String initialFileName ) {
        return askUserToSelectFileType( initialFileName, "Sélectionnez le fichier svp :", null );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters et Setters
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Getter de la référence au contrôleur de l'application.
     *
     * @return la référence au contrôleur de l'application
     */
    public ICtrlIhm getRefCtrl() {
        return _refCtrl;
    }

    /**
     * Setter de la référence au contrôleur de l'application.
     *
     * @param ctrl la nouvelle référence au contrôleur de l'application
     */
    public void setRefCtrl( ICtrlIhm ctrl ) {
        this._refCtrl = ctrl;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Construction des composants Swing par Matisse.
    ///////////////////////////////////////////////////////////////////////////
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupCompression = new javax.swing.ButtonGroup();
        buttonGroupEncryption = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldFilePath = new javax.swing.JTextField();
        jButtonSelectFile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPaneFileContent = new javax.swing.JScrollPane();
        jTextPaneFileContent = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonReadTextFile = new javax.swing.JButton();
        jButtonWriteTextFile = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabelLogoLogus = new javax.swing.JLabel();
        jLabelSwissSoftwareLogo = new javax.swing.JLabel();
        jButtonQuitter = new javax.swing.JButton();
        jLabelIconWebSiteLink = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonCompress = new javax.swing.JButton();
        jButtonEncryption = new javax.swing.JButton();
        jButtonDecompress = new javax.swing.JButton();
        jButtonDecryption = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jComboBoxCompressionAlgorithm = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jComboBoxEncryptionAlgorithm = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Fichiers texte ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 82, 147))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel1.setText("Chemin complet du fichier texte");

        jTextFieldFilePath.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        jButtonSelectFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/text-file-24.png"))); // NOI18N
        jButtonSelectFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectFileActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel2.setText("Contenu du fichier");

        jTextPaneFileContent.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jScrollPaneFileContent.setViewportView(jTextPaneFileContent);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jButtonReadTextFile.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonReadTextFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/read-64.png"))); // NOI18N
        jButtonReadTextFile.setText("Lire depuis fichier");
        jButtonReadTextFile.setEnabled(false);
        jButtonReadTextFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReadTextFileActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonReadTextFile);

        jButtonWriteTextFile.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonWriteTextFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/write-64.png"))); // NOI18N
        jButtonWriteTextFile.setText("Ecrire dans fichier");
        jButtonWriteTextFile.setEnabled(false);
        jButtonWriteTextFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWriteTextFileActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonWriteTextFile);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextFieldFilePath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSelectFile))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneFileContent))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSelectFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldFilePath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneFileContent, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelLogoLogus.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLogoLogus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLogoLogus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/emf_fribourg.jpg"))); // NOI18N
        jLabelLogoLogus.setToolTipText("Ca, c'est nous !");
        jLabelLogoLogus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelLogoLogus.setOpaque(true);

        jLabelSwissSoftwareLogo.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSwissSoftwareLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSwissSoftwareLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/swiss-made-software.png"))); // NOI18N
        jLabelSwissSoftwareLogo.setToolTipText("Battons-nous pour la qualité !");
        jLabelSwissSoftwareLogo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelSwissSoftwareLogo.setOpaque(true);

        jButtonQuitter.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonQuitter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/exit-48.png"))); // NOI18N
        jButtonQuitter.setText("Quitter");
        jButtonQuitter.setToolTipText("Pour quitter l'application");
        jButtonQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQuitterActionPerformed(evt);
            }
        });

        jLabelIconWebSiteLink.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelIconWebSiteLink.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelIconWebSiteLink.setText("<html><a href=\"http://www.iconfinder.com/\">https://www.emf.ch/</a></html>");
        jLabelIconWebSiteLink.setToolTipText("Cliquez-moi pour naviguer sur le site mentionné");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelSwissSoftwareLogo))
                    .addComponent(jButtonQuitter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelIconWebSiteLink)
                    .addComponent(jLabelLogoLogus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonQuitter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSwissSoftwareLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelLogoLogus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelIconWebSiteLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 82, 147));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/appIcon.png"))); // NOI18N
        jLabel4.setText("FileTools application v1.0");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Autres fonctionnalités avec les fichiers ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 82, 147))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        jButtonCompress.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonCompress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/zip-64.png"))); // NOI18N
        jButtonCompress.setText("Compresser");
        jButtonCompress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompressActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonCompress);

        jButtonEncryption.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonEncryption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/code-64.png"))); // NOI18N
        jButtonEncryption.setText("Encrypter");
        jButtonEncryption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEncryptionActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonEncryption);

        jButtonDecompress.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonDecompress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/unzip-64.png"))); // NOI18N
        jButtonDecompress.setText("Decompresser");
        jButtonDecompress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDecompressActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonDecompress);

        jButtonDecryption.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jButtonDecryption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ihm/resources/decode-64.png"))); // NOI18N
        jButtonDecryption.setText("Décrypter");
        jButtonDecryption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDecryptionActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonDecryption);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Algorithme de compression ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(0, 82, 147))); // NOI18N

        jComboBoxCompressionAlgorithm.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jComboBoxCompressionAlgorithm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxCompressionAlgorithm, 0, 252, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxCompressionAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Algorithme de cryptage ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(0, 82, 147))); // NOI18N

        jComboBoxEncryptionAlgorithm.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jComboBoxEncryptionAlgorithm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxEncryptionAlgorithm, 0, 252, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxEncryptionAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQuitterActionPerformed
        ihmExiting();
    }//GEN-LAST:event_jButtonQuitterActionPerformed

    private void jButtonSelectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectFileActionPerformed
        getRefCtrl().SelectFileActionPerformed();
    }//GEN-LAST:event_jButtonSelectFileActionPerformed

    private void jButtonReadTextFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReadTextFileActionPerformed
        getRefCtrl().ReadTextFileActionPerformed();
    }//GEN-LAST:event_jButtonReadTextFileActionPerformed

    private void jButtonWriteTextFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWriteTextFileActionPerformed
        getRefCtrl().WriteTextFileActionPerformed();
    }//GEN-LAST:event_jButtonWriteTextFileActionPerformed

    private void jButtonCompressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompressActionPerformed
        getRefCtrl().CompressActionPerformed();
    }//GEN-LAST:event_jButtonCompressActionPerformed

    private void jButtonDecompressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDecompressActionPerformed
        getRefCtrl().DecompressActionPerformed();
    }//GEN-LAST:event_jButtonDecompressActionPerformed

    private void jButtonEncryptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEncryptionActionPerformed
        getRefCtrl().EncryptActionPerformed();
    }//GEN-LAST:event_jButtonEncryptionActionPerformed

    private void jButtonDecryptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDecryptionActionPerformed
        getRefCtrl().DecryptActionPerformed();
    }//GEN-LAST:event_jButtonDecryptionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupCompression;
    private javax.swing.ButtonGroup buttonGroupEncryption;
    private javax.swing.JButton jButtonCompress;
    private javax.swing.JButton jButtonDecompress;
    private javax.swing.JButton jButtonDecryption;
    private javax.swing.JButton jButtonEncryption;
    private javax.swing.JButton jButtonQuitter;
    private javax.swing.JButton jButtonReadTextFile;
    private javax.swing.JButton jButtonSelectFile;
    private javax.swing.JButton jButtonWriteTextFile;
    private javax.swing.JComboBox jComboBoxCompressionAlgorithm;
    private javax.swing.JComboBox jComboBoxEncryptionAlgorithm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelIconWebSiteLink;
    private javax.swing.JLabel jLabelLogoLogus;
    private javax.swing.JLabel jLabelSwissSoftwareLogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPaneFileContent;
    private javax.swing.JTextField jTextFieldFilePath;
    private javax.swing.JTextPane jTextPaneFileContent;
    // End of variables declaration//GEN-END:variables
    /**
     * La référence au contrôleur de l'application.
     */
    private ICtrlIhm _refCtrl;
}
