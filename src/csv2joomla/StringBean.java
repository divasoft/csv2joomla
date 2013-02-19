package csv2joomla;

/**
 *
 * @author Develop
 */
public class StringBean {
    private String content;
    private String imageName;

    public StringBean() {
    }

    public StringBean(String content, String imageName) {
        this.content = content;
        this.imageName = imageName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content.trim();
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}