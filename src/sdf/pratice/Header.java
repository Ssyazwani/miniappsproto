package sdf.pratice;
import sdf.pratice.Main;

public class Header {
    private String category;
    private String app;
    private Float rating;

    public Header(String category, String app, float rating) {
        this.category = category;
        this.app = app;
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Float getAvg() {
        if (Integer.parseInt(category) != 0) {
            return rating / Integer.parseInt(category);
        } else {
            // Handle division by zero
            return null;
        }
    }

    
}
