package com.luania.witchpot.pojo;

import com.luania.witchpot.service.UserService;
import com.luania.witchpot.util.EnDecoderUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SegmentPojo implements Serializable {
    private String id = "";
    private String image = "";
    private String title = "NoTiTle";
    private String text = "";
    private String pid = "root";
    private String author = "";
    private long createTime = 0;

    public SegmentPojo(String text, String pid, String title, String image) {
        createTime = System.currentTimeMillis();
        this.text = EnDecoderUtil.encode(text);
        try {
            this.author = EnDecoderUtil.encode(UserService.getAutDATA().getUid());
        } catch (Exception e) {
            this.author = "匿名用户";
        }
        this.pid = pid;
        this.title = EnDecoderUtil.encode(title);
        if ("".equals(image)) {
            this.image = EnDecoderUtil.encode("noImage");
        } else {
            this.image = EnDecoderUtil.encode(image);
        }
    }

    public SegmentPojo() {
    }

    public String decodeText() {
        return EnDecoderUtil.decode(text);
    }

    public String decodeTitle() {
        return EnDecoderUtil.decode(title);
    }

    public String decodeImage() {
        return EnDecoderUtil.decode(image);
    }

    public String createTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(createTime));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
