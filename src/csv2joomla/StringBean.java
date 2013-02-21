package csv2joomla;

/**
 *
 * @author Develop
 */
public class StringBean {
    private String content="";
    private String intro="";
    private String title="";
    private String k2Id="";

    public StringBean() {
    }

    public StringBean(String content, String intro, String title, String k2Id) {
        this.content = content;
        this.intro = intro;
        this.title = title;
        this.k2Id = k2Id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro =  "<p>" + intro.trim() + "</p>";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        String[] str = content.split("\\|");
        if (str.length==3) {
            if (str[2].isEmpty()) {
                this.setTitle(str[0]);
                this.setIntro(str[1]);
                this.content="";
            } else {
                this.setTitle(str[0]);
                this.setIntro(str[1]);
                this.content="<p>"+str[2].replace("#", "</p><p>").replace("  ", "") +"</p>";
            }
        } else {
            System.err.println("Error parse content");
            this.content="ERROR";
        }
        /*if (str.length==1) {
            this.content="";
        } else {
            String body="";
            for (int i = 1; i < str.length; i++) {
                body+=str[i]+((i<str.length-1)?"</p><p>":"");
            }
            this.content="<p>"+body.trim()+"</p>";
        }*/
    }

    public String getK2Id() {
        return k2Id;
    }

    public void setK2Id(String k2Id) {
        this.k2Id = k2Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = "<p>"+title.trim()+"</p>";
    }
}