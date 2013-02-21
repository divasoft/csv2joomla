package csv2joomla;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

/**
 *
 * @author Develop
 */
public class Worker implements Runnable {
    private MainFrame core;
    private Thread thread;

    public Worker(MainFrame core) {
        this.core=core;
        init();
    }
    
    private void init() {
        thread = new Thread(this);
        thread.setName("Worker");
        thread.start();
    }

    @Override
    public void run() {
        core.resetPB();
        core.db = Util.readTextFile(core.pathDBtn.getText().trim());
        core.writeLog("Load: " + core.db.size());
        //System.out.println("Load: " + core.db.size());
        core.maxPB(core.db.size());

        List<String> tmFls = Util.readTextFile(core.pathImgBtn.getText().trim());
        core.writeLog("Load FS: " + tmFls.size());
        //System.out.println("Load FS: " + tmFls.size());

        for (Iterator<String> it = tmFls.iterator(); it.hasNext();) {
            String string = it.next();
            String[] tmf = string.replace("\"", "").split(";");
            String path = core.imgFromField.getText() + "/" + tmf[3].substring(0, 4) + "/" + tmf[3].substring(4, 8) + "/" + tmf[3].substring(8, 12) + "/" + tmf[2];
            core.dbFiles.add(new FileBean(tmf[0], path));
        }
        int cnt = Integer.parseInt(core.k2StartNumField.getText().trim());
        Pattern pattern = Pattern.compile("CCM:FID_([0-9]{1,5})");

        for (Iterator<String> it = core.db.iterator(); it.hasNext();) {
            String string = it.next();
            StringBean bean = new StringBean();

            bean.setContent((Jsoup.parseBodyFragment(string.replace("<p>&nbsp;</p>", "").replaceAll("</p><p.*?>", "#")).text().trim().replace("     ", "").replace("  ", "").replace("   ", "")));
            bean.setK2Id(Integer.toString(cnt++));
            if (!bean.getContent().equals("ERROR")) {
                core.dbClear.add(bean);
                if (core.copyImgButton.isSelected()) {
                    Matcher matcher = pattern.matcher(string);
                    if (matcher.find()) {
                        Util.findImage(matcher.group(1), core.dbFiles, Integer.toString(cnt), core.imgToField.getText(), core.imageMinPrefix.getSelectedItem().toString(), core.imageBigPrefix.getSelectedItem().toString(), core.k2ImgMinW.getText(), core.k2ImgMinH.getText(), core.k2ImgBigW.getText(), core.k2ImgBigH.getText(), core.resizeMin.isSelected(), core.resizeBig.isSelected());
                    }
                }
            }
            core.incPB();
        }

        String sql = core.sqlHeadArea.getText().trim() + "\n";
        int ord = Integer.parseInt(core.k2StartNumField.getText().trim());
        for (Iterator<StringBean> it = core.dbClear.iterator(); it.hasNext();) {
            StringBean bean = it.next();
            sql += core.replace(bean, Integer.toString(ord++)) + ((it.hasNext()) ? "," : "") + "\n";
        }
        core.SQL_READY=sql;

        core.fin();
    }
    
}