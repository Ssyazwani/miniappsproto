package sdf.pratice;
import sdf.pratice.Main;



public class Header {
    private String category;
    private String app;
    private static Float rating;

    public Header(String category, String app, Float rating) {
        this.category = category;
        this.app = app;
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public String getApp() {
        return app;
    }

    public Float getRating() {
        return rating;
    }

    // Hypothetical getAvg method
    public static Float getAvg() {
        // Perform your average calculation here
        // This is just a hypothetical implementation, adjust as needed
        if (rating != null && !Float.isNaN(rating)) {
            return rating / 2; // Adjust this calculation based on your requirements
        } else {
            return null; // or handle it according to your logic
        }
    }
}
