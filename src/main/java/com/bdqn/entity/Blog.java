package com.bdqn.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@TableName("t_blog")
public class Blog implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 博客编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客摘要信息
     */
    private String summary;

    /**
     * 博客发布时间
     */
    @TableField("releaseDate")
    @JSONField
    private Date releaseDate;

    /**
     * 点击阅读量
     */
    @TableField("clickHit")
    private Integer clickHit;

    /**
     * 评论数量
     */
    @TableField("replyHit")
    private Integer replyHit;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客所属分类
     */
    @TableField("typeId")
    private Integer typeId;

    /**
     * 关键词
     */
    @TableField("keyWord")
    private String keyWord;
    
    /**
     * 以下属性数据库中不存在
     * @return
     */
    @TableField(exist=false)
    private String releaseDateStr;    
    
    @TableField(exist=false)
    private Integer blogCount;

    @TableField(exist=false)
    private Blogtype blogtype;

    // 没有html 标签的博文文本内容
    @TableField(exist=false)
    private String contentNoTag;
    
    public String getContentNoTag() {
		return contentNoTag;
	}

	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}

	public Blogtype getBlogtype() {
		return blogtype;
	}

	public void setBlogtype(Blogtype blogtype) {
		this.blogtype = blogtype;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

	public Integer getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getClickHit() {
        return clickHit;
    }

    public void setClickHit(Integer clickHit) {
        this.clickHit = clickHit;
    }

    public Integer getReplyHit() {
        return replyHit;
    }

    public void setReplyHit(Integer replyHit) {
        this.replyHit = replyHit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "Blog{" +
        "id=" + id +
        ", title=" + title +
        ", summary=" + summary +
        ", releaseDate=" + releaseDate +
        ", clickHit=" + clickHit +
        ", replyHit=" + replyHit +
        ", content=" + content +
        ", typeId=" + typeId +
        ", keyWord=" + keyWord +
        "}";
    }
}
