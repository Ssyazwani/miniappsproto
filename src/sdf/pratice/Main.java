package sdf.pratice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

            Map<String, String> columnMap = readColumnsToMap(args[0], 1, 2);

            // Assuming you want to print or use the columnMap
            for (Map.Entry<String, String> entry : columnMap.entrySet()) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }

            Map<String, Float> result = calculateAverageRating(args[0], 1, 2 );
            for (Map.Entry<String, Float> enter : result.entrySet()) {
                System.out.printf("Category: %s, Average Rating: %.2f%n", enter.getKey(), enter.getValue());
            }

             System.out.println("Highest Ratings:");
             Map<String, Float> highestRatingApps = analyzeRatings(args[0],0, 1, 2 );
                    for (Entry<String, Float> entry : highestRatingApps.entrySet()) {
                        System.out.printf("Category: %s, Highest Rating App: %s, Rating: %.2f%n",
                                entry.getKey(), entry.getValue(), highestRatingApps.get(entry.getKey()));
                    }
            
                    // Print the lowest ratings
                    Map<String, Float> lowestRatingsApps = analyzeRatings(args[0],0, 1, 2 );
                    System.out.println("Lowest Ratings:");
                    for (Entry<String, Float> entry : lowestRatingsApps.entrySet()) {
                        System.out.printf("Category: %s, Lowest Rating App: %s, Rating: %.2f%n",
                                entry.getKey(), entry.getValue(), lowestRatingsApps.get(entry.getKey()));
                    }


        }

     }
    

    private static Map<String, String> readColumnsToMap(String filePath, int column1Index, int column2Index)
            throws IOException {
        Map<String, String> columnMap = new HashMap<>();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            // Skip the header if needed
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > Math.max(column1Index, column2Index)) {
                    String key = columns[column1Index].trim();
                    String value = columns[column2Index].trim();
                    if (!value.equalsIgnoreCase("NaN")) {
                        columnMap.put(key, value);
                }
            }
        }

        return columnMap;
    }
 }

 private static Map<String, Float> calculateAverageRating (String filePath, int column1Index, int column2Index) throws IOException {
    Map<String, Float> averageRatingMap = new HashMap<>();
    Map<String, Integer> categoryCountMap = new HashMap<>();

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

                // Check for NaN before adding to the computation
                if (!ratingStr.equalsIgnoreCase("NaN")) {
                    float rating = Float.parseFloat(ratingStr);

                    // Update total rating for the category
                    averageRatingMap.put(category, averageRatingMap.getOrDefault(category, 0f) + rating);

                    // Update count for the category
                    categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
                }
            }
        }
    }

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

    private static Map<String, Float> analyzeRatings(String filePath, int appIndex, int categoryIndex, int ratingIndex) throws IOException {
        Map<String, String> highestRatingApps = new HashMap<>();
        Map<String, String> lowestRatingApps = new HashMap<>();
        Map<String, Float> highestRatings = new HashMap<>();
        Map<String, Float> lowestRatings = new HashMap<>();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            // Skip the header if needed
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > Math.max(appIndex, Math.max(categoryIndex, ratingIndex))) {
                    String app = columns[appIndex].trim();
                    String category = columns[categoryIndex].trim();
                    String ratingStr = columns[ratingIndex].trim();

                    if (!ratingStr.equalsIgnoreCase("NaN")) {
                        float rating = Float.parseFloat(ratingStr);

                        // Update highest rating for the category
                        if (!highestRatingApps.containsKey(category) || rating > highestRatings.get(category)) {
                            highestRatingApps.put(category, app);
                            highestRatings.put(category, rating);
                        }

                        // Update lowest rating for the category
                        if (!lowestRatingApps.containsKey(category) || rating < lowestRatings.get(category)) {
                            lowestRatingApps.put(category, app);
                            lowestRatings.put(category, rating);
                        }
                    }                    
                }


            }
        }
        return lowestRatings;

    }
}



    
   
