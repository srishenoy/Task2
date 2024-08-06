package amazonSearch;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class OfficeChair {
	public static WebDriver driver ;

  @Test
  public void Chair() throws InterruptedException, IOException {
	  JavascriptExecutor js = (JavascriptExecutor)driver;
	  Properties prop=new Properties();
	  FileInputStream fs=new FileInputStream("src/main/resources/data.properties");
	  prop.load(fs);
	  String searchItem=prop.getProperty("searchitem");
		System.out.println(searchItem);
		Thread.sleep(10000);
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		Thread.sleep(1000);
		searchBox.sendKeys(searchItem);
		searchBox.submit();
		Thread.sleep(7000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".a-declarative")));
        List<WebElement> Products = driver.findElements(By.cssSelector(".a-declarative"));
        System.out.println("Number of products found: " + Products.size());
        Thread.sleep(4000);
        WebElement highestRatedProduct = null;
        double highestRating = 0;
        for (WebElement SearchChairs : Products) {
            try {
                WebElement ratingStar = SearchChairs.findElement(By.cssSelector("span.a-icon-alt"));
                WebElement reviewCount = SearchChairs.findElement(By.cssSelector("span.a-size-base"));

                String ratingText = ratingStar.getAttribute("innerHTML");
                double rating = Double.parseDouble(ratingText.split(" ")[0]);

                String reviewCountText = reviewCount.getText().replace(",", "");
                int reviewCounts = Integer.parseInt(reviewCountText);

                double combinedRating = rating * reviewCounts;

                if (combinedRating > highestRating) {
                    highestRating = combinedRating;
                    highestRatedProduct = SearchChairs;
                }
            } catch (Exception e) {
                System.out.println("ITEM MISSING " +e);
                continue;
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
  public void beforeTest() throws InterruptedException, IOException{
//	  
	  
	  Properties props=new Properties();
	  FileInputStream fs=new FileInputStream("src/main/resources/data.properties");
	  props.load(fs);
	  String browser = props.getProperty("browser");
	  if ("chrome".equalsIgnoreCase(browser)) {
		  ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-fullscreen");
			driver = new ChromeDriver(options); 
      } 
	  else
	  {
		  System.out.println("Browser not available =" +browser);
		  driver.quit();
	  }
		driver.get("https://www.amazon.com");
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
