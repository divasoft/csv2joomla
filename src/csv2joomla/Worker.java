package csv2joomla;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Develop
 */
public class Worker implements Runnable {

    private MainFrame core;
    private Thread thread;
    private boolean isAlive = false;

    public Worker(MainFrame core) {
        this.core = core;
        init();
    }

    private void init() {
        thread = new Thread(this);
        thread.setName("Worker");
        thread.start();
    }

    public void stop() {
        if (thread != null) {
            thread.stop();
        }
    }

    public boolean live() {
        return isAlive;
    }

    private void end() {
        String sql = core.sqlHeadArea.getText().trim() + "\n";
        //int ord = Integer.parseInt(core.k2StartNumField.getText().trim());
        for (Iterator<StringBean> it = core.dbClear.iterator(); it.hasNext();) {
            StringBean bean = it.next();
            sql += core.replace(bean, bean.getK2Id()) + ((it.hasNext()) ? "," : "") + "\n";
        }
        core.SQL_READY = sql;
        core.fin(true);
    }

    @Override
    public void run() {
        isAlive = true;
        if (!core.pathHtmlBtn.getText().equals("Select File HTML PART")) {
            try {
                core.resetPB();
                core.dbClear.clear();
                Document tables = Jsoup.parse(Util.getTextFile(core.pathHtmlBtn.getText().trim()));
                Elements elementsByTag = tables.getElementsByTag("tr");
                core.maxPB(elementsByTag.size());
                int cnt = Integer.parseInt(core.k2StartNumField.getText().trim());
                System.out.print("DELETE FROM `kmtt_k2_items` WHERE id IN(");
                for (Iterator<Element> it = elementsByTag.iterator(); it.hasNext();) {
                    Element element = it.next();
                    Elements elementsByStrong = element.getElementsByTag("strong");
                    String title = "";
                    for (Iterator<Element> it1 = elementsByStrong.iterator(); it1.hasNext();) {
                        Element element1 = it1.next();
                        title += element1.text();
                    }
                    String imgSrc = element.getElementsByTag("img").get(0).attr("src");
                    String image = new File(core.pathHtmlBtn.getText()).getParentFile().getAbsolutePath();
                    Timestamp timestamp = new Timestamp(new Date().getTime()-cnt*1000);
                    StringBean bean = new StringBean("", Jsoup.parseBodyFragment(element.html().replaceAll("<strong>.*?</strong>", "")).text(), title, cnt + "", timestamp+"");
                    File imgFile = new File(image + "/" + imgSrc);
                    if (core.copyAllImgButton.isSelected()) Util.saveImage(cnt+ "", imgFile, core);
                    core.dbClear.add(bean);
                    System.out.print(cnt+",");
                    cnt++;
                    core.incPB();
                }
                end();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                core.resetPB();
                core.db = Util.readTextFile(core.pathDBtn.getText().trim());
                //core.writeLog("Load: " + core.db.size());
                System.out.println("Load: " + core.db.size());
                core.maxPB(core.db.size());

                List<String> tmFls = Util.readTextFile(core.pathImgBtn.getText().trim());
                //core.writeLog("Load FS: " + tmFls.size());
                System.out.println("Load FS: " + tmFls.size());

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

                    bean.setContent((Jsoup.parseBodyFragment(string.replace("<p>&nbsp;</p>", "").replace("<p> </p>", "").replaceAll("</p><p.*?>", "#")).text().trim().replace("     ", "").replace("  ", "").replace("   ", "").replace("<p></p>", "")));
                    
                    if (!bean.getContent().equals("ERROR")) {
                        core.dbClear.add(bean);
                        if (core.copyAllImgButton.isSelected()) {
                            Matcher matcher = pattern.matcher(string);
                            if (matcher.find()) {
                                Util.findImage(matcher.group(1), Integer.toString(cnt), core);
                            }
                        }
                    }
                    bean.setK2Id(Integer.toString(cnt++));
                    core.incPB();
                }

                end();
            } catch (Exception ex) {
                core.fin(false);
                System.err.println(ex.getLocalizedMessage());
            }
            isAlive = false;
        }
    }
}