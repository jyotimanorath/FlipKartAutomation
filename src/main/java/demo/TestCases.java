package demo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");
        
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01() throws InterruptedException{
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(2000);
        WebElement searchBox=driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
       // searchBox.click();
        searchBox.sendKeys("washing machine");
        searchBox.submit();
        Thread.sleep(2000);
       WebElement sortBtn = driver.findElement(By.xpath("//div[@class='sHCOk2']/div[text()='Popularity']"));      
      sortBtn.click();   
      Thread.sleep(2000);
      List<WebElement> noOfProducts=driver.findElements(By.xpath("//div[@class='KzDlHZ']/following-sibling::div/span/div"));     
      int count=0;
      for(WebElement items:noOfProducts){
            String no=items.getText();
            Double rating=Double.parseDouble(no);
           
           if(rating <= 4){
            System.out.println(rating);
             count++;
           }
           
         }    
         System.out.println(count);
      System.out.println("end Test case: testCase02");
    }


}
