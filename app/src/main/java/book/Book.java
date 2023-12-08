package book;

import java.io.Serializable;

public class Book implements Serializable {
    private int resourceId;
    private String tenNuoc;
    private String mieuta;
    private String title;

    public Book(int resourceId, String tenNuoc, String mieuta, String title) {
        this.resourceId = resourceId;
        this.tenNuoc = tenNuoc;
        this.mieuta = mieuta;
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTenNuoc() {
        return tenNuoc;
    }

    public void setTenNuoc(String tenNuoc) {
        this.tenNuoc = tenNuoc;
    }

    public String getMieuta() {
        return mieuta;
    }

    public void setMieuta(String mieuta) {
        this.mieuta = mieuta;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
