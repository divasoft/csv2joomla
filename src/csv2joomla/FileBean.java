package csv2joomla;

import java.io.File;

/**
 *
 * @author Develop
 */
public class FileBean {
    private String id;
    private File image;

    public FileBean() {
    }

    public FileBean(String id, File image) {
        this.id = id;
        this.image = image;
    }
    
    public FileBean(String id, String image) {
        this(id, new File(image));
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}