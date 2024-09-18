package Model.Blog;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Blog {
    private int blogID;
    private String title;
    private String content;
    private Date publicDate;
    private int userID;
    private boolean status;

    public Blog() {
    }

    public Blog(int blogID, String title, String content, Date publicDate, int userID, boolean status) {
        this.blogID = blogID;
        this.title = title;
        this.content = content;
        this.publicDate = publicDate;
        this.userID = userID;
        this.status = status;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Blog{" + "blogID=" + blogID + ", title=" + title + ", content=" + content + ", publicDate=" + publicDate + ", userID=" + userID + ", status=" + status + '}';
    }
    
    
}
