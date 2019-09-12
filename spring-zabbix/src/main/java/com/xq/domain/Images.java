package com.xq.domain;

public class Images {
    private Long imageid;

    private Integer imagetype;

    private String name;

    private byte[] image;

    public Long getImageid() {
        return imageid;
    }

    public void setImageid(Long imageid) {
        this.imageid = imageid;
    }

    public Integer getImagetype() {
        return imagetype;
    }

    public void setImagetype(Integer imagetype) {
        this.imagetype = imagetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}