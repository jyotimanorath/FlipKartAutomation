package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class SeleniumWrapper {
  ChromeDriver driver;

 
    public boolean search_product(WebDriver driver2,By locator,String textToSend){
        Boolean success;
try {
   
    WebElement searchbox=driver2.findElement(locator);
    searchbox.sendKeys(textToSend);
    searchbox.submit();
    success=true;
} catch (Exception e) {
    // TODO: handle exception
    System.out.println("Unable to search the product");
    e.printStackTrace();
    success=false;
}      
  return success;
  
    }

    public ArrayList<String> searchStarAndPrint(WebDriver driver, By locatorOfTitle, By locatorOfRating, double starRating) {
        System.out.println("Printing products with more with certain attribute");
        List<WebElement> titles = driver.findElements(locatorOfTitle);
        List<WebElement> attr = driver.findElements(locatorOfRating);
        System.out.println("Found Titles: "+titles.size()+" and attribute "+attr.size());
        ArrayList<String> results = new ArrayList<>();

        if(attr.size()!=titles.size()){
            System.out.println("Mismatch in size of array");
            if(attr.size()>titles.size()) attr = sliceArrayList(attr, titles.size());
            else titles = sliceArrayList(titles, attr.size());
        }
        
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i).getText();
            String attribute = attr.get(i).getText();
            attribute = removeSubstring(attribute, "% off");
            float attributeFloat = 0;
            try {
                // Convert rating from String to Float
                attributeFloat = Float.parseFloat(attribute);
                
                // Check if rating is greater than or equal to 4.0
                if (attributeFloat >= starRating) {
                    // If condition is met, add to the results list
                    results.add(title + " has a attribute of " + attributeFloat);
                }
            } catch (NumberFormatException e) {
                // Handle potential NumberFormatException for invalid float strings
                System.out.println("Invalid rating format for: " + title + ", rating: " + attributeFloat);
            }
        }
        
        return results;
    }

    
 
    public Boolean click(WebDriver driver, By locator) {
        System.out.println("Clicking");
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement button = driver.findElement(locator);
            button.click();
            success = true;
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
            success = false;
        }
        return success;
    }

    public boolean sortBtn(WebDriver driver2, By locator) {
        Boolean success;
try {
            // TODO Auto-generated method stub
            
            WebElement sortButton=driver2.findElement(locator);
            sortButton.click();
            success=true;
    
} catch (Exception e) {
    System.out.println("unable to click on sort button");
    e.printStackTrace();;
    success=false;
    // TODO: handle exception
}

        return success;

    }


    public List<String> searchHighestReview(WebDriver driver, By locatorOfTitle, By locatorOfAttribute) {
        System.out.println("Printing products with more with certain attribute");
        List<WebElement> titles = driver.findElements(locatorOfTitle);
        List<WebElement> attr = driver.findElements(locatorOfAttribute);
        System.out.println("Found Titles: "+titles.size()+" and attribute "+attr.size());
        HashMap<String, Integer> results = new HashMap<String, Integer>();

        if(attr.size()!=titles.size()){
            System.out.println("Mismatch in size of array, modifying to adjut");
            if(attr.size()>titles.size()) attr = sliceArrayList(attr, titles.size());
            else titles = sliceArrayList(titles, attr.size());
        }
        
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i).getText();
            String attribute = attr.get(i).getText();
            attribute = removeSubstring(removeSubstring(attribute, "("),")").replace(",", ""); // Making it parseable
            Integer attributeInt = 0;
            try {
                // Convert rating from String to Float
                // DEBUG
                // System.out.println("Parsing attribute - "+attribute);
                attributeInt = Integer.parseInt(attribute);
                results.putIfAbsent(title, attributeInt);
                
            } catch (NumberFormatException e) {
                // Handle potential NumberFormatException for invalid float strings
                System.out.println("Invalid rating format for: " + title);
            }
        }
        
        return getTopFiveKeys(results);
    }

    public static List<String> getTopFiveKeys(Map<String, Integer> map) {
        return map.entrySet()
                  .stream()
                  .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                  .limit(5)
                  .map(Map.Entry::getKey)
                  .collect(Collectors.toList());
    }
    public static String removeSubstring(String originalString, String substringToRemove) {
        // Check if the original string contains the specified substring
        if (originalString.contains(substringToRemove)) {
            // Remove the substring from the original string
            return originalString.replace(substringToRemove, "").trim();
        }
        // Return the original string if it does not contain the substring
        return originalString;
    }
    public static <T> List<T> sliceArrayList(List<T> list, int n) {
        // Ensure the size n does not exceed the list size
        n = Math.min(n, list.size());
        // Create a new ArrayList from the sublist to ensure it's a separate list
        return new ArrayList<>(list.subList(0, n));
    }
}
    

