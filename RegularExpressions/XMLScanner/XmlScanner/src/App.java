import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws Exception, IOException {
        // Example: reeading a plant catalogue 
        // Create a HashMap to store the plant catalogue 
        HashMap<String, HashMap<String, String>> plantCatalog = new HashMap<>();
        try {
            File file = new File("plant_catalog.xml");
            Scanner scanner = new Scanner(file);

            // Pattern to match the opening tag of a plant element
            Pattern plantStartPattern = Pattern.compile("<PLANT>");
            Pattern plantEndPattern = Pattern.compile("</PLANT>");
            Pattern tagPattern  = Pattern.compile("<(\\w+)>(.*)</(\\1+)>"); // \\1 means that the content must be the same of the first group and \\w means that the group must match a word 
            
            String currentPlantTag = null;
            HashMap<String, String> currentPlant = null;

            //Read each line of the XML file 

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Check if the current line contains the start of a new plant element
                Matcher plantStartMatcher = plantStartPattern.matcher(line); 
                if (plantStartMatcher.find()) {
                    // Create a new HashMap corresponding to the current plant element
                    currentPlant = new HashMap<>();

                }
                //Check if the current line contains the end of the current plant element 
                Matcher plantEndMatcher = plantEndPattern.matcher(line);
                if (plantEndMatcher.find()) {
                    // Add the current plant to the catalog

                    if (currentPlant != null && currentPlantTag != null) {
                        plantCatalog.put(currentPlantTag, currentPlant);
                    }
                    currentPlant = null;
                    currentPlantTag = null;
                }

                // CHeck if the current line contains a subtag of a plant and its value inside a plant element

                Matcher tagMatcher = tagPattern.matcher(line);
                if (tagMatcher.find()) {
                    // Extract the subtag and its value 
                    String subtag = tagMatcher.group(1);
                    String value = tagMatcher.group(2);

                    if (currentPlant != null ) {
                        // Add the subtag and value to the current plant
                        currentPlant.put(subtag, value);
                        //Use subtag COMMON as the key in the catalog
                        if (currentPlantTag == null) { // first subtag that I'm reading --> I want to use Common name as key in hashmap
                            currentPlantTag = value;
                        }
                    }
                }  
            }
            scanner.close();
        } 
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        for (String plantTag : plantCatalog.keySet()) {
            HashMap<String, String> plant = plantCatalog.get(plantTag);
            System.out.println("Plant Tag: "+plantTag);
            for (String subtag : plant.keySet()) {
                String value = plant.get(subtag);
                System.out.println(subtag+": "+value);
            }
            System.out.println("\n");
        }    
    }
}
