package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCases {
static ChromeDriver driver;

@BeforeSuite( alwaysRun = true)
public static void openurl()
{   
  driver = new ChromeDriver();
  driver.manage().window().maximize();
 // WebDriverManager.chromedriver().timeout(30).setup();
  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
 
  }

   @Test(enabled=true)
    public static void testCase01(){
   try{
    driver.get("https://www.flipkart.com/");
    SeleniumWrapper selenium = new SeleniumWrapper();
    System.out.println("Start Test case: testCase01");
       selenium.search_product(driver, By.xpath("//input[@placeholder='Search for Products, Brands and More']"), "washing machine");
        Thread.sleep(2000);      
      Boolean popularitylink=selenium.sortBtn(driver, (By.xpath("//div[@class='sHCOk2']/div[text()='Popularity']")));
      if(popularitylink){
      List<WebElement> noOfProducts=driver.findElements(By.xpath("//div[@class='KzDlHZ']/following-sibling::div/span/div"));     
      int count=0;
      for(WebElement items:noOfProducts){
            String no=items.getText();
            Double rating=Double.parseDouble(no);
           
           if(rating <= 4){
            System.out.println(rating);
             count++;
             System.out.println("TestCase01: pass");
           }
           
         }    
         System.out.println(count);
    
}else{
    System.out.println("Select sort by is not popularity");
    System.out.println("TestCase01: fail");
}}
catch(Exception e){
  System.out.println("not able to search");
  e.printStackTrace();
}
}

@Test(priority = 1, description = "Search “iPhone”, print the Titles and discount % of items with more than 17% discount")
public void testCase02() throws InterruptedException{
    System.out.println("Beginning Test Case 02");

    double discountPercent = 10.0;
    // Go to Flipkart
    driver.get("http://www.flipkart.com");
    SeleniumWrapper selenium = new SeleniumWrapper();
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    // Search iPhone
    Boolean flow1Result = selenium.search_product(driver, By.xpath("//button[@aria-label='Search for Products, Brands and More']/../div/input"), "iPhone");
    if(flow1Result){
        System.out.println("Flow 1 success");
    }
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    // Print titles with more than 17% Discount
    ArrayList<String> flow2Result =selenium.searchStarAndPrint(driver, By.xpath("//*[contains(text(), 'Ratings') and not(./*) and not(contains(text(), 'Customer'))]/../../../../div[1]"), By.xpath("//span[contains(., '% off')]"), discountPercent);
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    for(String res : flow2Result){
        System.out.println(res);
    }
    System.out.println("Ending Test Case 02");   
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
}


@Test(priority = 3, description = "Search “Coffee Mug”, select 4 stars and above, and print the Title and image URL of the 5 items with highest number of reviews")
public void testCase03() throws InterruptedException{
    System.out.println("Beginning Test Case 03");
    driver.get("http://www.flipkart.com");
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    SeleniumWrapper selenium = new SeleniumWrapper();
    selenium.search_product(driver, By.xpath("//button[@aria-label='Search for Products, Brands and More']/../div/input"), "Coffee Mug");
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    selenium.click(driver, By.xpath("//div[text()='4★ & above']//ancestor::div[1]//div[1]"));
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    List<String> result = selenium.searchHighestReview(driver, 
                                                // Old Xpath
                                                // By.xpath("//a[@rel='noopener noreferrer'][2]"),
                                                By.xpath("//div[@class='slAVV4']//a[@rel='noopener noreferrer'][2]"),
                                                // Old Xpath
                                                // By.xpath("//a[@rel='noopener noreferrer'][2]/../div[3]//span[2]"));
                                                By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']"));
    for(String s: result){
        System.out.println(s);
    }
    Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
    System.out.println("Ending Test Case 03");
}


@AfterTest
public static void aftertest(){
  driver.close();
}
@AfterSuite
public static void aftersuite(){
  driver.quit();
}
}

