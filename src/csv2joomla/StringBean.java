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
    private String date="";

    public StringBean() {
    }

    public StringBean(String content, String intro, String title, String k2Id, String date) {
        this.content = content;
        this.intro = intro;
        this.title = title;
        this.k2Id = k2Id;
        this.date = date;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro =  "<p>" + intro.replace("'", "").trim() + "</p>";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        String[] str = content.replace("'", "").split("\\|");
        if (str.length==4) {
            if (str[2].isEmpty()) {
                this.setTitle(str[0]);
                this.setIntro(str[1]);
                this.content="";
                this.date="";
            } else {
                this.setTitle(str[0]);
                this.setIntro(str[1]);
                this.content="<p>"+str[2].replace("#", "</p><p>").replace("  ", "").replace("<p></p>", "").replace("<p> </p>", "").replace("<p>·</p>", "") +"</p>";
                this.setDate(str[3]);
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
        this.title = title.replace("'", "").replace("« ", "«").trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}