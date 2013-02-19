package csv2joomla;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        if (fileChooser.showOpenDialog(fileChooser)==JFileChooser.APPROVE_OPTION)
        {
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
}
