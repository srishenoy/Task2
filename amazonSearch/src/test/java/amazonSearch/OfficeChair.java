package amazonSearch;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class OfficeChair {
	public static WebDriver driver ;

  @Test
  public void Chair() throws InterruptedException {
	  JavascriptExecutor js = (JavascriptExecutor)driver;
	  
//		System.setProperty("webdriver.chrome.verboseLogging", "true");
		Thread.sleep(10000);
		WebElement InputBox =driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		InputBox.click();
		Thread.sleep(1000);
		InputBox.sendKeys("Office Chairs");
		WebElement search= driver.findElement(By.xpath("//span[@id='nav-search-submit-text']"));
		search.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
	
		List<WebElement> Chairs = driver.findElements(By.cssSelector(".s-main-slot .s-result-item"));
        WebElement highestRatedProduct = null;
        double highestRating = 0;
        for (WebElement OfficeChairs : Chairs) {
            try {
                WebElement ratingStar = OfficeChairs.findElement(By.cssSelector("span.a-icon-alt"));
                WebElement reviewCount = OfficeChairs.findElement(By.cssSelector("span.a-size-base"));

                String ratingText = ratingStar.getAttribute("innerHTML");
                double rating = Double.parseDouble(ratingText.split(" ")[0]);

                String reviewCountText = reviewCount.getText().replace(",", "");
                int reviewCounts = Integer.parseInt(reviewCountText);

                double combinedRating = rating * reviewCounts;

                if (combinedRating > highestRating) {
                    highestRating = combinedRating;
                    highestRatedProduct = OfficeChairs;
                }
            } catch (Exception e) {
                System.out.println("ITEM MISSING");
                driver.quit();
            }
        }

        if (highestRatedProduct != null) {
            String selectedProductTitle = highestRatedProduct.findElement(By.cssSelector("span.a-text-normal")).getText();

            highestRatedProduct.findElement(By.cssSelector("span.a-text-normal")).click();

            Thread.sleep(3000); 
            WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
            addToCartButton.click();

            Thread.sleep(2000); 
            driver.findElement(By.id("nav-cart")).click();

            Thread.sleep(4000);
            WebElement cartItem = driver.findElement(By.cssSelector("span.a-truncate-cut"));

            if (cartItem.getText().equals(selectedProductTitle)) {
                System.out.println("Test Passed: The correct product was added to the cart.");
            } else {
                System.out.println("Test Failed: The product in the cart does not match the selected product.");
            }
        } else {
            System.out.println("No product found with a rating on the first page.");
        }
		
		
  }
  @AfterMethod
  public void afterMethod() {
//	  if(ITestResult.FAILURE==result.getStatus())
//		{
//			try{
//				File error = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//				FileUtils.copyFile(error, new File("error.png"));
//				LogEntries logs = driver.manage().logs().get("driver");
//				System.out.println(logs);
//				driver.quit();
//			}
//		catch (Exception e)
//		{
//			System.out.println("Exception while taking screenshot "+e.getMessage());
//		}	
//		}
	  driver.quit();
  }

  @BeforeClass
  public void beforeClass() throws InterruptedException {
		
  }

  @BeforeTest
  public void beforeTest() throws InterruptedException{
	  ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-fullscreen");
		driver = new ChromeDriver(options); 
		driver.get("https://www.amazon.com");
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
