package in.a_comic.a_comic;

public class DashboardCategoryModel {
    private String title;
    private String views;
    private String noOfFiles;
    private String imageUrl;
    private String document;

    public DashboardCategoryModel(String title, String views, String noOfFiles, String imageUrl, String document) {
        this.title = title;
        this.views = views;
        this.noOfFiles = noOfFiles;
        this.imageUrl = imageUrl;
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getNoOfFiles() {
        return noOfFiles;
    }

    public void setNoOfFiles(String noOfFiles) {
        this.noOfFiles = noOfFiles;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
