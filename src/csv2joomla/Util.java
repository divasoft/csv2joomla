package csv2joomla;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.imgscalr.Scalr;

/**
 *
 * @author Develop
 */
public class Util {

    public static void setTextButton(JButton jButton, String header, String ext, String text) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(header);
        fileChooser.setFileFilter(new FileNameExtensionFilter(text, ext));
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            jButton.setText(fileChooser.getSelectedFile().getAbsolutePath().toString());
        }
    }
    
    public static void getSavedFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save SQL");
        fileChooser.setFileFilter(new FileNameExtensionFilter("SQL *.sql", "sql"));
        if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            Util.saveTextFile(fileChooser.getSelectedFile().getAbsolutePath().toString(), content);
        }
    }

    public static List<String> readTextFile(String patch) {
        try {
            InputStream is = new FileInputStream(patch);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            List<String> result = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            br.close();
            isr.close();
            is.close();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void saveTextFile(String patch, String text) {
        try {
            File file = new File(patch);
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
            BufferedWriter out = new BufferedWriter(writer);
            out.write(text);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void findImage(String id, List<FileBean> list, String cnt, String to, String prefib_min, String prefix_big, String wMin, String hMin, String wBig, String hBig, boolean rMin, boolean rBig) {
        for (Iterator<FileBean> it = list.iterator(); it.hasNext();) {
            FileBean fileBean = it.next();
            if (fileBean.getId().equals(id)) {
                String minFile=to+"/"+getMD5(cnt)+"_"+prefib_min+".jpg";
                String bigFile=to+"/"+getMD5(cnt)+"_"+prefix_big+".jpg";
                
                if (rMin) {
                    Util.resizeImage(fileBean.getImage(), new File(minFile),Integer.parseInt(wMin),Integer.parseInt(hMin));
                } else {
                    Util.copyFile(fileBean.getImage(), new File(minFile));
                }
                
                if (rBig) {
                    Util.resizeImage(fileBean.getImage(), new File(bigFile),Integer.parseInt(wBig),Integer.parseInt(hBig));
                } else {
                    Util.copyFile(fileBean.getImage(), new File(bigFile));
                }
            }
        }
    }
    
    public static void resizeImage(File image, File to, int w, int h) {
        try {
            BufferedImage img = ImageIO.read(image);
            BufferedImage res_img = Scalr.resize(img, w, h);
            ImageIO.write(res_img, "jpg", to);
            System.out.println("IMG["+w+"*"+h+"]: "+image.toPath()+"->"+to.toPath());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            //ex.printStackTrace();
        }
    }

    /**
     *
     * @param str getMD5 by string
     * @return md5(string)
     */
    public static String getMD5(String str) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(), 0, str.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    public static void copyFile(File from, File to) {
        try {
            Files.copy(from.toPath(), to.toPath());
            System.out.println("IMG: "+from.toPath()+"->"+to.toPath());
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}