package sdf.pratice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import sdf.pratice.Header;

public class Main{

    public static void main (String[]args) throws IOException{
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
        }
    }


    public static void categorize(BufferedReader br) throws IOException {
        br.readLine(); // Skip the header

        Map<String, Float> categoryTotalRating = new HashMap<>();
        Map<String, Integer> categoryCount = new HashMap<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] columns = line.trim().split(",");
            Header app = new Header(columns[0], columns[1], Float.parseFloat(columns[2]));

            // Assuming Header.getAvg() calculates the average based on existing data.
            Float avg = app.getAvg();

            // Update categoryTotalRating and categoryCount for each category
            if (avg != null) {
                String category = app.getCategory();

                // Update total rating for the category
                categoryTotalRating.put(category, categoryTotalRating.getOrDefault(category, 0f) + avg);

                // Update count for the category
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }
        }
    

        // Calculate and print the average rating for each category
        for (Map.Entry<String, Float> entry : categoryTotalRating.entrySet()) {
            String category = entry.getKey();
            float totalRating = entry.getValue();
            int count = categoryCount.get(category);

            float averageRating = totalRating / count;

            System.out.printf("Category: %s, Average Rating: %.2f%n", category, averageRating);
        }
    

    }
}

    


