package in.a_comic.a_comic;

import java.io.File;

public class OfflineFileDataModel {
    private String title;
    private String size;
    private String modifiedTime;
    private File file;

    public OfflineFileDataModel(String title, String size, String modifiedTime, File file) {
        this.title = title;
        this.size = size;
        this.modifiedTime = modifiedTime;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
