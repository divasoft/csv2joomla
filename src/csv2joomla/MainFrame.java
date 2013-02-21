package csv2joomla;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

/**
 *
 * @author Develop
 */
public class MainFrame extends javax.swing.JFrame {

    public String SQL_READY="";
    public List<String> db = new ArrayList<>();
    public List<StringBean> dbClear = new ArrayList<>();
    public List<FileBean> dbFiles = new ArrayList<>();
    public String sqlHead = "INSERT INTO `kmtt_k2_items` (`id`, `title`, `alias`, `catid`, `published`, `introtext`, `fulltext`, `video`, `gallery`, `extra_fields`, `extra_fields_search`, `created`, `created_by`, `created_by_alias`, `checked_out`, `checked_out_time`, `modified`, `modified_by`, `publish_up`, `publish_down`, `trash`, `access`, `ordering`, `featured`, `featured_ordering`, `image_caption`, `image_credits`, `video_caption`, `video_credits`, `hits`, `params`, `metadesc`, `metadata`, `metakey`, `plugins`, `language`) VALUES";
    public String sqlBody = "({id}, '{title}', '{alias}', {catid}, 1, '{introtext}', '{fulltext}', NULL, NULL, '[]', '', '2013-02-19 11:02:15', 653, '', 653, '2013-02-20 11:45:01', '0000-00-00 00:00:00', 0, '2013-02-19 11:02:15', '0000-00-00 00:00:00', 0, 1, {ordering}, 0, 0, '', '', '', '', 0, '{\"catItemTitle\":\"\",\"catItemTitleLinked\":\"\",\"catItemFeaturedNotice\":\"\",\"catItemAuthor\":\"\",\"catItemDateCreated\":\"\",\"catItemRating\":\"\",\"catItemImage\":\"\",\"catItemIntroText\":\"\",\"catItemExtraFields\":\"\",\"catItemHits\":\"\",\"catItemCategory\":\"\",\"catItemTags\":\"\",\"catItemAttachments\":\"\",\"catItemAttachmentsCounter\":\"\",\"catItemVideo\":\"\",\"catItemVideoWidth\":\"\",\"catItemVideoHeight\":\"\",\"catItemAudioWidth\":\"\",\"catItemAudioHeight\":\"\",\"catItemVideoAutoPlay\":\"\",\"catItemImageGallery\":\"\",\"catItemDateModified\":\"\",\"catItemReadMore\":\"\",\"catItemCommentsAnchor\":\"\",\"catItemK2Plugins\":\"\",\"itemDateCreated\":\"\",\"itemTitle\":\"\",\"itemFeaturedNotice\":\"\",\"itemAuthor\":\"\",\"itemFontResizer\":\"\",\"itemPrintButton\":\"\",\"itemEmailButton\":\"\",\"itemSocialButton\":\"\",\"itemVideoAnchor\":\"\",\"itemImageGalleryAnchor\":\"\",\"itemCommentsAnchor\":\"\",\"itemRating\":\"\",\"itemImage\":\"\",\"itemImgSize\":\"\",\"itemImageMainCaption\":\"\",\"itemImageMainCredits\":\"\",\"itemIntroText\":\"\",\"itemFullText\":\"\",\"itemExtraFields\":\"\",\"itemDateModified\":\"\",\"itemHits\":\"\",\"itemCategory\":\"\",\"itemTags\":\"\",\"itemAttachments\":\"\",\"itemAttachmentsCounter\":\"\",\"itemVideo\":\"\",\"itemVideoWidth\":\"\",\"itemVideoHeight\":\"\",\"itemAudioWidth\":\"\",\"itemAudioHeight\":\"\",\"itemVideoAutoPlay\":\"\",\"itemVideoCaption\":\"\",\"itemVideoCredits\":\"\",\"itemImageGallery\":\"\",\"itemNavigation\":\"\",\"itemComments\":\"\",\"itemTwitterButton\":\"\",\"itemFacebookButton\":\"\",\"itemGooglePlusOneButton\":\"\",\"itemAuthorBlock\":\"\",\"itemAuthorImage\":\"\",\"itemAuthorDescription\":\"\",\"itemAuthorURL\":\"\",\"itemAuthorEmail\":\"\",\"itemAuthorLatest\":\"\",\"itemAuthorLatestLimit\":\"\",\"itemRelated\":\"\",\"itemRelatedLimit\":\"\",\"itemRelatedTitle\":\"\",\"itemRelatedCategory\":\"\",\"itemRelatedImageSize\":\"\",\"itemRelatedIntrotext\":\"\",\"itemRelatedFulltext\":\"\",\"itemRelatedAuthor\":\"\",\"itemRelatedMedia\":\"\",\"itemRelatedImageGallery\":\"\",\"itemK2Plugins\":\"\"}', '', 'robots=\nauthor=', '', '', '*')";

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        imageBigPrefix.setSelectedIndex(2);
    }

    public String replace(StringBean bean, String ordering) {
        try {
            return sqlBody.replace("{id}", bean.getK2Id()).replace("{title}", bean.getTitle()).replace("{alias}", "!!!!").replace("{catid}", k2CatId.getText()).replace("{introtext}", bean.getIntro()).replace("{fulltext}", bean.getContent()).replace("{ordering}", ordering);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return "";
        }
    }

    public void parse() {
        db = Util.readTextFile(pathDBtn.getText().trim());
        System.out.println("Load: " + db.size());

        List<String> tmFls = Util.readTextFile(pathImgBtn.getText().trim());
        System.out.println("Load FS: " + tmFls.size());

        for (Iterator<String> it = tmFls.iterator(); it.hasNext();) {
            String string = it.next();
            String[] tmf = string.replace("\"", "").split(";");
            String path = imgFromField.getText() + "/" + tmf[3].substring(0, 4) + "/" + tmf[3].substring(4, 8) + "/" + tmf[3].substring(8, 12) + "/" + tmf[2];
            dbFiles.add(new FileBean(tmf[0], path));
        }
        int cnt = Integer.parseInt(k2StartNumField.getText().trim());
        Pattern pattern = Pattern.compile("CCM:FID_([0-9]{1,5})");

        for (Iterator<String> it = db.iterator(); it.hasNext();) {
            String string = it.next();
            StringBean bean = new StringBean();
            if (copyImgButton.isSelected()) {
                Matcher matcher = pattern.matcher(string);
                if (matcher.find()) {
                    Util.findImage(matcher.group(1), dbFiles, Integer.toString(cnt), imgToField.getText(), imageMinPrefix.getSelectedItem().toString(), imageBigPrefix.getSelectedItem().toString(), k2ImgMinW.getText(), k2ImgMinH.getText(), k2ImgBigW.getText(), k2ImgBigH.getText(), resizeMin.isSelected(), resizeBig.isSelected());
                }
            }
            bean.setContent((Jsoup.parseBodyFragment(string.replace("<p>&nbsp;</p>", "").replaceAll("</p><p.*?>", "#")).text().trim().replace("     ", "").replace("  ", "").replace("   ", "")));
            bean.setK2Id(Integer.toString(cnt++));
            if (!bean.getContent().equals("ERROR")) {
                dbClear.add(bean);
            }
        }

        String sql = sqlHead + "\n";
        int ord = Integer.parseInt(k2StartNumField.getText().trim());
        for (Iterator<StringBean> it = dbClear.iterator(); it.hasNext();) {
            StringBean bean = it.next();
            sql += replace(bean, Integer.toString(ord++)) + ((it.hasNext()) ? "," : "") + "\n";
        }
        SQL_READY=sql;
        outSqlArea.append(sql);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        outSqlArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        k2CatId = new javax.swing.JTextField();
        goBtn = new javax.swing.JButton();
        generatorBtn = new javax.swing.JButton();
        imgToField = new javax.swing.JTextField();
        imgFromField = new javax.swing.JTextField();
        copyImgButton = new javax.swing.JCheckBox();
        pathImgBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        k2StartNumField = new javax.swing.JTextField();
        imageMinPrefix = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        k2ImgMinH = new javax.swing.JTextField();
        k2ImgBigH = new javax.swing.JTextField();
        imageBigPrefix = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        resizeBig = new javax.swing.JCheckBox();
        resizeMin = new javax.swing.JCheckBox();
        k2ImgMinW = new javax.swing.JTextField();
        k2ImgBigW = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSV into Joomla K2 SQL (c) Divasoft, inc.");

        pathDBtn.setText("Select CSV");
        pathDBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathDBtnActionPerformed(evt);
            }
        });

        outSqlArea.setColumns(20);
        outSqlArea.setRows(5);
        jScrollPane1.setViewportView(outSqlArea);

        jLabel1.setText("Joomla K2 category ID");

        k2CatId.setText("3");

        goBtn.setText("GO!");
        goBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBtnActionPerformed(evt);
            }
        });

        generatorBtn.setText("Save SQL");
        generatorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatorBtnActionPerformed(evt);
            }
        });

        imgToField.setText("d:\\_db\\READY");

        imgFromField.setText("d:\\_db\\IMG");

        copyImgButton.setSelected(true);
        copyImgButton.setText("CopyFiles");

        pathImgBtn.setText("Select Files CSV");
        pathImgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathImgBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Joomla K2 item Start Num");

        k2StartNumField.setText("100");

        imageMinPrefix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "S", "M", "L", "XL" }));

        jLabel3.setText("Joomla K2 Image Min HW");

        k2ImgMinH.setText("150");

        k2ImgBigH.setText("450");

        imageBigPrefix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "S", "M", "L", "XL" }));

        jLabel4.setText("Joomla K2 Image Big HW");

        resizeBig.setText("Resize Big");

        resizeMin.setSelected(true);
        resizeMin.setText("Resize Min");

        k2ImgMinW.setText("150");

        k2ImgBigW.setText("450");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pathImgBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pathDBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(imgFromField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyImgButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imgToField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(k2ImgBigH, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(k2ImgMinH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(k2CatId, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(k2StartNumField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addComponent(generatorBtn))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(k2ImgBigW)
                                    .addComponent(k2ImgMinW, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(imageBigPrefix, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(imageMinPrefix, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resizeBig)
                                    .addComponent(resizeMin))))))
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
                .addComponent(pathImgBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyImgButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(k2CatId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(k2StartNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatorBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(k2ImgMinH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(k2ImgBigH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(k2ImgMinW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(k2ImgBigW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(imageMinPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resizeMin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(imageBigPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resizeBig))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        Util.getSavedFile(SQL_READY);
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
    private javax.swing.JCheckBox copyImgButton;
    private javax.swing.JButton generatorBtn;
    private javax.swing.JButton goBtn;
    private javax.swing.JComboBox imageBigPrefix;
    private javax.swing.JComboBox imageMinPrefix;
    private javax.swing.JTextField imgFromField;
    private javax.swing.JTextField imgToField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField k2CatId;
    private javax.swing.JTextField k2ImgBigH;
    private javax.swing.JTextField k2ImgBigW;
    private javax.swing.JTextField k2ImgMinH;
    private javax.swing.JTextField k2ImgMinW;
    private javax.swing.JTextField k2StartNumField;
    private javax.swing.JTextArea outSqlArea;
    private javax.swing.JButton pathDBtn;
    private javax.swing.JButton pathImgBtn;
    private javax.swing.JCheckBox resizeBig;
    private javax.swing.JCheckBox resizeMin;
    // End of variables declaration//GEN-END:variables
}
