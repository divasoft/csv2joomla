package csv2joomla;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Develop
 */
public class Util {
    public static String setTextButton(JButton jButton, String prefix, String header, String ext, String text) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(header);
        fileChooser.setFileFilter(new FileNameExtensionFilter(text, ext));
        if (fileChooser.showOpenDialog(fileChooser)==JFileChooser.APPROVE_OPTION)
        {
            jButton.setText(prefix+fileChooser.getSelectedFile().getName().toString());
            return fileChooser.getSelectedFile().getAbsolutePath().toString();
        }
        return "";
    }
}
