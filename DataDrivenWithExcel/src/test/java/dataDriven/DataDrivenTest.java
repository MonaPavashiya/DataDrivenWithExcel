package dataDriven;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;



public class DataDrivenTest {
	
	Logger log = Logger.getLogger(DataDrivenTest.class);
	
	WebDriver driver;
	
	@BeforeClass
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", 
				"C:/Users/mS/Documents/MonaQAjars/WebDrivers/chromedriver.exe");
	}
	@BeforeMethod
	public void intialization() {
		driver =new ChromeDriver();
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@Test(dataProvider="excelData")
	public void login(String username, String password) {
		
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		
		if (driver.getPageSource().contains("Epic sadface")) 
		{
			driver.quit();
			Assert.assertTrue(false);
		} else {
			
			Assert.assertTrue(true);
		}
		System.out.println(username+ password );
		log.info("Info..........");
		log.debug("Debug..................");
	}
	
	
	@DataProvider(name="excelData")
	public Object[][] getData()
	{
		Object[][] testObjArray = null;
		try {
			testObjArray = ExcelUtility.getTableArray("C:\\Users\\mS\\Documents"
					        + "\\Mona_QA material\\excelData.xlsx", "Sheet1");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return testObjArray;
	}
	
	
	
	@AfterMethod
	public void closeBrowser() {
		driver.quit();
	}
}
