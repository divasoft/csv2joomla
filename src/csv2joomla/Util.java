package csv2joomla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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

    public static void findImage(String id, List<FileBean> list, String cnt, String to) {
        String ret = "";
        for (Iterator<FileBean> it = list.iterator(); it.hasNext();) {
            FileBean fileBean = it.next();
            if (fileBean.getId().equals(id)) {
                Util.copyFile(fileBean.getImage(), new File(to+"/"+getMD5(cnt)+"_L.jpg"));
                //return fileBean.getCopyImage();
            }
        }
        //return ret;
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
            System.out.println(from.toPath()+"->"+to.toPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}