package sdf.pratice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            System.out.println("Please insert CSV file");
            System.exit(1);
        }

        System.out.println("Proceeding to read file");

        try (FileReader fr = new FileReader(args[0]);
             BufferedReader br = new BufferedReader(fr)) {

            br.readLine(); // Skip the header

            String row;
            Set<String> categoryNames = new HashSet<>();

            while ((row = br.readLine()) != null) {
                String[] field = row.trim().split(",");
                // Adding category names to the set
                categoryNames.add(field[1]);
            }

            // Printing out unique category names
            System.out.println("Unique Category Names:");
            for (String category : categoryNames) {
                System.out.println(category);
            }

            Map<String, Map<String, List<Float>>> categoryMap = analyzeAndPrintRatings(args[0]);
            for (Map.Entry<String, Map<String, List<Float>>> categoryEntry : categoryMap.entrySet()) {
                String category = categoryEntry.getKey();
            
                System.out.println("Category: " + category);
            
                // Find the highest and lowest rating applications for the category
                String highestAppName = null;
                String lowestAppName = null;
                float highestRating = Float.MIN_VALUE;
                float lowestRating = Float.MAX_VALUE;
            
            Map<String, List<Float>> appRatingMap = categoryEntry.getValue();
            for (Map.Entry<String, List<Float>> appEntry : appRatingMap.entrySet()) {
                String appName = appEntry.getKey();  // Application name
                List<Float> ratings = appEntry.getValue();  // List of ratings
            
                // Find the highest and lowest rating for the application
                float maxRating = Collections.max(ratings);
                float minRating = Collections.min(ratings);
            
                    // Check and update the highest rating application
                if (maxRating > highestRating) {
                highestRating = maxRating;
                highestAppName = appName;
                    }
            
                // Check and update the lowest rating application
                if (minRating < lowestRating) {
                    lowestRating = minRating;
                    lowestAppName = appName;
                    }
                }
            
                // Print the highest and lowest rating applications for the category
                System.out.println("  Highest Rating Application: " + highestAppName + " (Rating: " + highestRating + ")");
                System.out.println("  Lowest Rating Application: " + lowestAppName + " (Rating: " + lowestRating + ")");
                System.out.println();  // Add a newline between categories
            }
            
            Map<String, Float> result = calculateAverageRating(args[0], 1, 2 );
            for (Map.Entry<String, Float> enter : result.entrySet()) {
                System.out.printf("Category: %s, Average Rating: %.2f%n", enter.getKey(), enter.getValue());
            }

            Map<String, Integer> NumofApps = totalNum(args[0], 0, 1);
            for (Map.Entry<String, Integer> entry : NumofApps.entrySet()) {
            System.out.printf("Category: %s, Number of Apps: %d%n", entry.getKey(), entry.getValue());
                }
            
             int lines = totalLines(args[0]);
            System.out.println("Total lines in the file: " + lines);


        }

     }
    

 private static Map<String, Float> calculateAverageRating (String filePath, int column1Index, int column2Index) throws IOException {
    
    Map<String, Float> averageRatingMap = new HashMap<>();
    Map<String, Integer> categoryCountMap = new HashMap<>();
    int nanCount = 0;

    try (FileReader fileReader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(fileReader)) {

        // Skip the header if needed
        bufferedReader.readLine();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] columns = line.split(",");
            if (columns.length > Math.max(column1Index, column2Index)) {
                String category = columns[column1Index].trim();
                String ratingStr = columns[column2Index].trim();

               
                if (!ratingStr.equalsIgnoreCase("NaN")) {
                    float rating = Float.parseFloat(ratingStr);

                    
                    averageRatingMap.put(category, averageRatingMap.getOrDefault(category, 0f) + rating);

                    
                    categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
                } else {
                    nanCount++;
            }
        }
    }

    System.out.println("Number of discarded values ignored: " + nanCount);

    // Calculate and store the average rating for each category
    Map<String, Float> result = new HashMap<>();
    for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
        String category = entry.getKey();
        int count = entry.getValue();
        float totalRating = averageRatingMap.getOrDefault(category, 0f);

        if (count > 0) {
            float averageRating = totalRating / count;
            result.put(category, averageRating);
        
        }

    }

    return result;
}

    }

        private static Map<String, Map<String, List<Float>>> analyzeAndPrintRatings (String filePath) {
        
        Map<String, Map<String, List<Float>>> categoryMap = new HashMap<>();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            bufferedReader.readLine(); // Skip the header

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 3) {
                    String app = columns[0].trim();
                    String category = columns[1].trim();
                    String ratingStr = columns[2].trim();

                    if (!ratingStr.equalsIgnoreCase("NaN")) {
                        float rating = Float.parseFloat(ratingStr);

                        // Update categoryMap
                        categoryMap.computeIfAbsent(category, k -> new HashMap<>());
                    Map<String, List<Float>> appRatingMap = categoryMap.get(category);

                        appRatingMap.computeIfAbsent(app, k -> new ArrayList<>());
                        List<Float> ratings = appRatingMap.get(app);

                        ratings.add(rating);
                    }
                }
            }

            

        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryMap;
    }

    
    

    private static Map<String, Integer> totalNum(String filePath, int appIndex, int categoryIndex) throws IOException {
        Map<String, Integer> NumofApps = new HashMap<>();
    
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
    
            bufferedReader.readLine(); // Skip the header
    
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > Math.max(appIndex, categoryIndex)) {
                    String app = columns[appIndex].trim();
                    String category = columns[categoryIndex].trim();
    
                    // Increment the count for the category
                    NumofApps.put(category, NumofApps.getOrDefault(category, 0) + 1);
                }
            }
    
            return NumofApps;
        }
    }

    private static int totalLines(String filePath) throws IOException {
        int totalLines = 0;
    
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
    
            bufferedReader.readLine(); // Skip the header
    
            while (bufferedReader.readLine() != null) {
                totalLines++;
            }
        }
    
        return totalLines;
    }

}
    
  


    
   
