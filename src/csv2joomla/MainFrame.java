package csv2joomla;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Develop
 */
public class MainFrame extends javax.swing.JFrame implements ICore {

    public String SQL_READY = "";
    public List<String> db = new ArrayList<>();
    public List<StringBean> dbClear = new ArrayList<>();
    public List<FileBean> dbFiles = new ArrayList<>();
    public Worker worker = null;
    //public String sqlHead = "INSERT INTO `kmtt_k2_items` (`id`, `title`, `alias`, `catid`, `published`, `introtext`, `fulltext`, `video`, `gallery`, `extra_fields`, `extra_fields_search`, `created`, `created_by`, `created_by_alias`, `checked_out`, `checked_out_time`, `modified`, `modified_by`, `publish_up`, `publish_down`, `trash`, `access`, `ordering`, `featured`, `featured_ordering`, `image_caption`, `image_credits`, `video_caption`, `video_credits`, `hits`, `params`, `metadesc`, `metadata`, `metakey`, `plugins`, `language`) VALUES";
    //public String sqlBody = "({id}, '{title}', '{alias}', {catid}, 1, '{introtext}', '{fulltext}', NULL, NULL, '[]', '', '2013-02-19 11:02:15', 653, '', 653, '2013-02-20 11:45:01', '0000-00-00 00:00:00', 0, '2013-02-19 11:02:15', '0000-00-00 00:00:00', 0, 1, {ordering}, 0, 0, '', '', '', '', 0, '{\"catItemTitle\":\"\",\"catItemTitleLinked\":\"\",\"catItemFeaturedNotice\":\"\",\"catItemAuthor\":\"\",\"catItemDateCreated\":\"\",\"catItemRating\":\"\",\"catItemImage\":\"\",\"catItemIntroText\":\"\",\"catItemExtraFields\":\"\",\"catItemHits\":\"\",\"catItemCategory\":\"\",\"catItemTags\":\"\",\"catItemAttachments\":\"\",\"catItemAttachmentsCounter\":\"\",\"catItemVideo\":\"\",\"catItemVideoWidth\":\"\",\"catItemVideoHeight\":\"\",\"catItemAudioWidth\":\"\",\"catItemAudioHeight\":\"\",\"catItemVideoAutoPlay\":\"\",\"catItemImageGallery\":\"\",\"catItemDateModified\":\"\",\"catItemReadMore\":\"\",\"catItemCommentsAnchor\":\"\",\"catItemK2Plugins\":\"\",\"itemDateCreated\":\"\",\"itemTitle\":\"\",\"itemFeaturedNotice\":\"\",\"itemAuthor\":\"\",\"itemFontResizer\":\"\",\"itemPrintButton\":\"\",\"itemEmailButton\":\"\",\"itemSocialButton\":\"\",\"itemVideoAnchor\":\"\",\"itemImageGalleryAnchor\":\"\",\"itemCommentsAnchor\":\"\",\"itemRating\":\"\",\"itemImage\":\"\",\"itemImgSize\":\"\",\"itemImageMainCaption\":\"\",\"itemImageMainCredits\":\"\",\"itemIntroText\":\"\",\"itemFullText\":\"\",\"itemExtraFields\":\"\",\"itemDateModified\":\"\",\"itemHits\":\"\",\"itemCategory\":\"\",\"itemTags\":\"\",\"itemAttachments\":\"\",\"itemAttachmentsCounter\":\"\",\"itemVideo\":\"\",\"itemVideoWidth\":\"\",\"itemVideoHeight\":\"\",\"itemAudioWidth\":\"\",\"itemAudioHeight\":\"\",\"itemVideoAutoPlay\":\"\",\"itemVideoCaption\":\"\",\"itemVideoCredits\":\"\",\"itemImageGallery\":\"\",\"itemNavigation\":\"\",\"itemComments\":\"\",\"itemTwitterButton\":\"\",\"itemFacebookButton\":\"\",\"itemGooglePlusOneButton\":\"\",\"itemAuthorBlock\":\"\",\"itemAuthorImage\":\"\",\"itemAuthorDescription\":\"\",\"itemAuthorURL\":\"\",\"itemAuthorEmail\":\"\",\"itemAuthorLatest\":\"\",\"itemAuthorLatestLimit\":\"\",\"itemRelated\":\"\",\"itemRelatedLimit\":\"\",\"itemRelatedTitle\":\"\",\"itemRelatedCategory\":\"\",\"itemRelatedImageSize\":\"\",\"itemRelatedIntrotext\":\"\",\"itemRelatedFulltext\":\"\",\"itemRelatedAuthor\":\"\",\"itemRelatedMedia\":\"\",\"itemRelatedImageGallery\":\"\",\"itemK2Plugins\":\"\"}', '', 'robots=\nauthor=', '', '', '*')";

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        try {
            System.out.println("<<< [Redirect System.out/System.err into DebugConsole] >>>");

            final PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream(pis);  // throws IOException
            PrintStream ps = new PrintStream(pos);
            System.setOut(ps);
            System.setErr(ps);
            Thread t = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                byte[] buf = new byte[8192];
                                int bytesRead = 0;
                                while ((bytesRead = pis.read(buf)) != -1) {
                                    consoleArea.append(new String(buf, 0, bytesRead));
                                }
                            } catch (Exception ioe) {
                                //ioe.printStacktrace();
                                System.err.println("Fatal Error:  Exiting reader.");
                            } finally {
                                try {
                                    pis.close();
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
            t.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String replace(StringBean bean, String ordering) {
        try {
            return patternSqlArea.getText().trim().replace("{id}", bean.getK2Id()).replace("{title}", bean.getTitle()).replace("{alias}", Util.toTranslite(bean.getTitle().replace(" ", "-"))).replace("{catid}", k2CatId.getText()).replace("{introtext}", bean.getIntro()).replace("{fulltext}", bean.getContent()).replace("{ordering}", ordering).replace("{date_add}", bean.getDate());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return "";
        }
    }

    public void parse() {
        SQL_READY = "";
        if (db != null) {
            db.clear();
        }
        if (dbClear != null) {
            dbClear.clear();
        }
        if (dbFiles != null) {
            dbFiles.clear();
        }

        if (worker != null && worker.live()) {
            worker.stop();
            worker = null;
            resetPB();
            goBtn.setText("GO");
        } else {
            consoleArea.setText("");
            goBtn.setText("STOP");
            worker = new Worker(this);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pathDBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        k2CatId = new javax.swing.JTextField();
        goBtn = new javax.swing.JButton();
        generatorBtn = new javax.swing.JButton();
        copyImgButton = new javax.swing.JCheckBox();
        pathImgBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        k2StartNumField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        sqlHeadArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        patternSqlArea = new javax.swing.JTextArea();
        jSpinner1 = new javax.swing.JSpinner();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panelXS = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        k2ImgXSW = new javax.swing.JTextField();
        k2ImgXSH = new javax.swing.JTextField();
        resizeXS = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        panelS = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        k2ImgSW = new javax.swing.JTextField();
        k2ImgSH = new javax.swing.JTextField();
        resizeS = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        panelM = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        k2ImgMW = new javax.swing.JTextField();
        k2ImgMH = new javax.swing.JTextField();
        resizeM = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        panelL = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        k2ImgLW = new javax.swing.JTextField();
        k2ImgLH = new javax.swing.JTextField();
        resizeL = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        panelXL = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        k2ImgXLW = new javax.swing.JTextField();
        k2ImgXLH = new javax.swing.JTextField();
        resizeXL = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        imgSrcToField = new javax.swing.JTextField();
        imgToField = new javax.swing.JTextField();
        imgFromField = new javax.swing.JTextField();
        copyAllImgButton = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSV into Joomla K2 SQL & Image resize (c) Divasoft, inc.");

        pathDBtn.setText("Select DB Articles CSV");
        pathDBtn.setToolTipText("<html><body>Format (without \\\"): <b>title|intro|fulltext</b></body></html>");
        pathDBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathDBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Joomla K2 category ID");

        k2CatId.setText("3");
        k2CatId.setToolTipText("Integer");

        goBtn.setText("GO");
        goBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBtnActionPerformed(evt);
            }
        });

        generatorBtn.setText("Save SQL");
        generatorBtn.setEnabled(false);
        generatorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatorBtnActionPerformed(evt);
            }
        });

        copyImgButton.setSelected(true);
        copyImgButton.setText("CopySRCFiles");

        pathImgBtn.setText("Select Files DB images CSV");
        pathImgBtn.setToolTipText("<html><body>\nFormat: <b>standart CSV</b>\n</body></html>");
        pathImgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathImgBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Joomla K2 item Start Num");

        k2StartNumField.setText("300");
        k2StartNumField.setToolTipText("Integer");

        jLabel3.setText("Split sql out file");

        consoleArea.setColumns(20);
        consoleArea.setFont(new java.awt.Font("Lucida Console", 0, 9)); // NOI18N
        consoleArea.setRows(5);
        jScrollPane1.setViewportView(consoleArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Console Out", jPanel1);

        sqlHeadArea.setColumns(20);
        sqlHeadArea.setRows(5);
        sqlHeadArea.setText("INSERT INTO `kmtt_k2_items` (`id`, `title`, `alias`, `catid`, `published`, `introtext`, `fulltext`, `video`, `gallery`, `extra_fields`, `extra_fields_search`, `created`, `created_by`, `created_by_alias`, `checked_out`, `checked_out_time`, `modified`, `modified_by`, `publish_up`, `publish_down`, `trash`, `access`, `ordering`, `featured`, `featured_ordering`, `image_caption`, `image_credits`, `video_caption`, `video_credits`, `hits`, `params`, `metadesc`, `metadata`, `metakey`, `plugins`, `language`) VALUES");
        jScrollPane3.setViewportView(sqlHeadArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("SQL Head", jPanel2);

        patternSqlArea.setColumns(20);
        patternSqlArea.setRows(5);
        patternSqlArea.setText("({id}, '{title}', '{alias}', {catid}, 1, '{introtext}', '{fulltext}', NULL, NULL, '[]', '', '{date_add}', 653, '', 653, '{date_add}', '0000-00-00 00:00:00', 0, '2013-02-19 11:02:15', '0000-00-00 00:00:00', 0, 1, {ordering}, 0, 0, '', '', '', '', 0, '{\"catItemTitle\":\"\",\"catItemTitleLinked\":\"\",\"catItemFeaturedNotice\":\"\",\"catItemAuthor\":\"\",\"catItemDateCreated\":\"\",\"catItemRating\":\"\",\"catItemImage\":\"\",\"catItemIntroText\":\"\",\"catItemExtraFields\":\"\",\"catItemHits\":\"\",\"catItemCategory\":\"\",\"catItemTags\":\"\",\"catItemAttachments\":\"\",\"catItemAttachmentsCounter\":\"\",\"catItemVideo\":\"\",\"catItemVideoWidth\":\"\",\"catItemVideoHeight\":\"\",\"catItemAudioWidth\":\"\",\"catItemAudioHeight\":\"\",\"catItemVideoAutoPlay\":\"\",\"catItemImageGallery\":\"\",\"catItemDateModified\":\"\",\"catItemReadMore\":\"\",\"catItemCommentsAnchor\":\"\",\"catItemK2Plugins\":\"\",\"itemDateCreated\":\"\",\"itemTitle\":\"\",\"itemFeaturedNotice\":\"\",\"itemAuthor\":\"\",\"itemFontResizer\":\"\",\"itemPrintButton\":\"\",\"itemEmailButton\":\"\",\"itemSocialButton\":\"\",\"itemVideoAnchor\":\"\",\"itemImageGalleryAnchor\":\"\",\"itemCommentsAnchor\":\"\",\"itemRating\":\"\",\"itemImage\":\"\",\"itemImgSize\":\"\",\"itemImageMainCaption\":\"\",\"itemImageMainCredits\":\"\",\"itemIntroText\":\"\",\"itemFullText\":\"\",\"itemExtraFields\":\"\",\"itemDateModified\":\"\",\"itemHits\":\"\",\"itemCategory\":\"\",\"itemTags\":\"\",\"itemAttachments\":\"\",\"itemAttachmentsCounter\":\"\",\"itemVideo\":\"\",\"itemVideoWidth\":\"\",\"itemVideoHeight\":\"\",\"itemAudioWidth\":\"\",\"itemAudioHeight\":\"\",\"itemVideoAutoPlay\":\"\",\"itemVideoCaption\":\"\",\"itemVideoCredits\":\"\",\"itemImageGallery\":\"\",\"itemNavigation\":\"\",\"itemComments\":\"\",\"itemTwitterButton\":\"\",\"itemFacebookButton\":\"\",\"itemGooglePlusOneButton\":\"\",\"itemAuthorBlock\":\"\",\"itemAuthorImage\":\"\",\"itemAuthorDescription\":\"\",\"itemAuthorURL\":\"\",\"itemAuthorEmail\":\"\",\"itemAuthorLatest\":\"\",\"itemAuthorLatestLimit\":\"\",\"itemRelated\":\"\",\"itemRelatedLimit\":\"\",\"itemRelatedTitle\":\"\",\"itemRelatedCategory\":\"\",\"itemRelatedImageSize\":\"\",\"itemRelatedIntrotext\":\"\",\"itemRelatedFulltext\":\"\",\"itemRelatedAuthor\":\"\",\"itemRelatedMedia\":\"\",\"itemRelatedImageGallery\":\"\",\"itemK2Plugins\":\"\"}', '', 'robots=\\nauthor=', '', '', '*')");
        jScrollPane2.setViewportView(patternSqlArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("SQL pattern", jPanel3);

        jLabel4.setText("Width");

        k2ImgXSW.setText("100");
        k2ImgXSW.setToolTipText("width");

        k2ImgXSH.setText("100");
        k2ImgXSH.setToolTipText("height");

        resizeXS.setSelected(true);
        resizeXS.setText("Resize");

        jLabel6.setText("Height");

        javax.swing.GroupLayout panelXSLayout = new javax.swing.GroupLayout(panelXS);
        panelXS.setLayout(panelXSLayout);
        panelXSLayout.setHorizontalGroup(
            panelXSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelXSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgXSW, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgXSH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resizeXS)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelXSLayout.setVerticalGroup(
            panelXSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelXSLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(panelXSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(k2ImgXSH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k2ImgXSW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(resizeXS)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        jTabbedPane2.addTab("XS", panelXS);

        jLabel7.setText("Width");

        k2ImgSW.setText("150");
        k2ImgSW.setToolTipText("width");

        k2ImgSH.setText("150");
        k2ImgSH.setToolTipText("height");

        resizeS.setSelected(true);
        resizeS.setText("Resize");

        jLabel8.setText("Height");

        javax.swing.GroupLayout panelSLayout = new javax.swing.GroupLayout(panelS);
        panelS.setLayout(panelSLayout);
        panelSLayout.setHorizontalGroup(
            panelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgSW, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgSH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resizeS)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelSLayout.setVerticalGroup(
            panelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(panelSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(k2ImgSH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k2ImgSW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(resizeS)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        jTabbedPane2.addTab("S", panelS);

        jLabel9.setText("Width");

        k2ImgMW.setText("200");
        k2ImgMW.setToolTipText("width");

        k2ImgMH.setText("200");
        k2ImgMH.setToolTipText("height");

        resizeM.setSelected(true);
        resizeM.setText("Resize");

        jLabel10.setText("Height");

        javax.swing.GroupLayout panelMLayout = new javax.swing.GroupLayout(panelM);
        panelM.setLayout(panelMLayout);
        panelMLayout.setHorizontalGroup(
            panelMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgMW, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgMH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resizeM)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelMLayout.setVerticalGroup(
            panelMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(panelMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(k2ImgMH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k2ImgMW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(resizeM)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        jTabbedPane2.addTab("M", panelM);

        jLabel11.setText("Width");

        k2ImgLW.setText("300");
        k2ImgLW.setToolTipText("width");

        k2ImgLH.setText("300");
        k2ImgLH.setToolTipText("height");

        resizeL.setSelected(true);
        resizeL.setText("Resize");

        jLabel12.setText("Height");

        javax.swing.GroupLayout panelLLayout = new javax.swing.GroupLayout(panelL);
        panelL.setLayout(panelLLayout);
        panelLLayout.setHorizontalGroup(
            panelLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgLW, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgLH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resizeL)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelLLayout.setVerticalGroup(
            panelLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(panelLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(k2ImgLH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k2ImgLW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(resizeL)
                    .addComponent(jLabel12))
                .addContainerGap())
        );

        jTabbedPane2.addTab("L", panelL);

        jLabel13.setText("Width");

        k2ImgXLW.setText("400");
        k2ImgXLW.setToolTipText("width");

        k2ImgXLH.setText("400");
        k2ImgXLH.setToolTipText("height");

        resizeXL.setSelected(true);
        resizeXL.setText("Resize");

        jLabel14.setText("Height");

        javax.swing.GroupLayout panelXLLayout = new javax.swing.GroupLayout(panelXL);
        panelXL.setLayout(panelXLLayout);
        panelXLLayout.setHorizontalGroup(
            panelXLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelXLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgXLW, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(k2ImgXLH, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resizeXL)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelXLLayout.setVerticalGroup(
            panelXLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelXLLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(panelXLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(k2ImgXLH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(k2ImgXLW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(resizeXL)
                    .addComponent(jLabel14))
                .addContainerGap())
        );

        jTabbedPane2.addTab("XL", panelXL);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Out dir's"));

        imgSrcToField.setText("d:\\_db\\SRC");
        imgSrcToField.setToolTipText("Location output file");

        imgToField.setText("d:\\_db\\READY");
        imgToField.setToolTipText("Location output file");

        imgFromField.setText("d:\\_db\\IMG");
        imgFromField.setToolTipText("Location original images");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgToField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgFromField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgSrcToField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(imgFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgSrcToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        copyAllImgButton.setSelected(true);
        copyAllImgButton.setText("CopyImages");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pathImgBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pathDBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(goBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(generatorBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(k2CatId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(k2StartNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(236, 236, 236))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(copyAllImgButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(copyImgButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathDBtn)
                    .addComponent(goBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathImgBtn)
                    .addComponent(generatorBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(k2CatId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(k2StartNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(copyImgButton)
                            .addComponent(copyAllImgButton)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pathDBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathDBtnActionPerformed
        Util.setTextButton(pathDBtn, "Select CSV (db)", "csv", "File *.csv");
    }//GEN-LAST:event_pathDBtnActionPerformed

    private void goBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBtnActionPerformed
        parse();
    }//GEN-LAST:event_goBtnActionPerformed

    private void pathImgBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathImgBtnActionPerformed
        Util.setTextButton(pathImgBtn, "Select CSV (Files location)", "csv", "File *.csv");
    }//GEN-LAST:event_pathImgBtnActionPerformed

    private void generatorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatorBtnActionPerformed
        Util.getSavedFile(SQL_READY,Integer.parseInt(jSpinner1.getValue().toString()));
    }//GEN-LAST:event_generatorBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea consoleArea;
    public javax.swing.JCheckBox copyAllImgButton;
    public javax.swing.JCheckBox copyImgButton;
    public javax.swing.JButton generatorBtn;
    public javax.swing.JButton goBtn;
    public javax.swing.JTextField imgFromField;
    public javax.swing.JTextField imgSrcToField;
    public javax.swing.JTextField imgToField;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JTextField k2CatId;
    public javax.swing.JTextField k2ImgLH;
    public javax.swing.JTextField k2ImgLW;
    public javax.swing.JTextField k2ImgMH;
    public javax.swing.JTextField k2ImgMW;
    public javax.swing.JTextField k2ImgSH;
    public javax.swing.JTextField k2ImgSW;
    public javax.swing.JTextField k2ImgXLH;
    public javax.swing.JTextField k2ImgXLW;
    public javax.swing.JTextField k2ImgXSH;
    public javax.swing.JTextField k2ImgXSW;
    public javax.swing.JTextField k2StartNumField;
    private javax.swing.JPanel panelL;
    private javax.swing.JPanel panelM;
    private javax.swing.JPanel panelS;
    private javax.swing.JPanel panelXL;
    private javax.swing.JPanel panelXS;
    public javax.swing.JButton pathDBtn;
    public javax.swing.JButton pathImgBtn;
    public javax.swing.JTextArea patternSqlArea;
    public javax.swing.JProgressBar progressBar;
    public javax.swing.JCheckBox resizeL;
    public javax.swing.JCheckBox resizeM;
    public javax.swing.JCheckBox resizeS;
    public javax.swing.JCheckBox resizeXL;
    public javax.swing.JCheckBox resizeXS;
    public javax.swing.JTextArea sqlHeadArea;
    // End of variables declaration//GEN-END:variables

    @Override
    public void resetPB() {
        progressBar.setValue(0);
    }

    @Override
    public void incPB() {
        progressBar.setValue(progressBar.getValue() + 1);
        progressBar.setToolTipText("" + progressBar.getValue() + " / " + progressBar.getMaximum() + "");
    }

    @Override
    public void maxPB(int max) {
        progressBar.setMaximum(max);
    }

    @Override
    public void fin(boolean ok) {
        System.out.println("============================");
        System.out.println("======     " + ((ok) ? "!FIN!" : "FAIL.") + "      ======");
        System.out.println("============================");

        if (!SQL_READY.isEmpty()) {
            generatorBtn.setEnabled(true);
            generatorBtnActionPerformed(null);
        }
        goBtn.setText("GO");
    }

    /*@Override
     public void writeLog(String str) {
     outSqlArea.append(str+"\n");
     }*/
}
