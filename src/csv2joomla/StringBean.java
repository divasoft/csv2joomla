package csv2joomla;

/**
 *
 * @author Develop
 */
public class StringBean {
    private String content;
    private String k2Id;

    public StringBean() {
    }

    public StringBean(String content, String k2Id) {
        this.content = content;
        this.k2Id = k2Id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content.trim();
    }

    public String getK2Id() {
        return k2Id;
    }

    public void setK2Id(String k2Id) {
        this.k2Id = k2Id;
    }
}