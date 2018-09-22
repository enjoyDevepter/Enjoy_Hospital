package cn.ehanmy.hospital.mvp.model.entity.activity;

import java.io.Serializable;

public class ActivityInfoBean implements Serializable {
    private String title;
    private String content;
    private String image;

    @Override
    public String toString() {
        return "ActivityInfoBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                '}';
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
