package in.a_comic.a_comic;

public class ComicCardDataModel {
    private String title;
    private String description;
    private String image_url;
    private String views;
    private String size;
    private String fileUrl;

    public ComicCardDataModel(String title, String description, String image_url, String views, String size, String fileUrl) {
        this.title = title;
        this.description = description;
        this.image_url = image_url;
        this.views = views;
        this.size = size;
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getViews() {
        return views;
    }

    public String getSize() {
        return size;
    }
}
