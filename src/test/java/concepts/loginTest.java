package concepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class loginTest {

	WebDriver driver;
	String browser;
	String url;

	By usernameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@id='password']");
	By loginField = By.xpath("//button[@name='login']");
	By dashboardField = By.xpath("//span[text()='Dashboard']");
	By customerMenuField = By.xpath("//span[text()='Customers']");
	By addCustomerField = By.xpath("//ul[@id='side-menu']/li[3]/ul/li[1]/a");
	By addCustomer_HeaderField = By.xpath("//div[@id='page-wrapper']/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/h5");
	By fullnameField = By.xpath("//input[@id='account']");
	By companyDropDownField = By.xpath("//select[@id='cid']");
	By emailField = By.xpath("//input[@id='email']");
	By phoneField = By.xpath("//input[@id='phone']");
	By addressField = By.xpath("//input[@id='address']");
	By cityField = By.xpath("//input[@id='city']");
	By stateField = By.xpath("//input[@id='state']");
	By zipField = By.xpath("//input[@id='zip']");
	By countryField = By.xpath("//select[@id='country']");
	By tagField = By.xpath("//select[@id='tags']");
	By currencyField = By.xpath("//select[@id='currency']");
	By groupField = By.xpath("//select[@id='group']");
	By passwordContactField = By.xpath("//input[@id='password']");
	By confirmPassField = By.xpath("//input[@id='cpassword']");
	By submitContact = By.xpath("//button[@id='submit']");
	
	
	String USERNAME = "demo@techfios.com" ;
	String PASSWORD = "abc123";
	String DASHBOARD_HEADER_TEXT = "Dashboard";
	String ADDCONTACT_HEADER_TEXT = "Add Contact";
	String FULLNAME = "WORLDWIDE";
	String EMAIL = "abcef@gmail.com";
	String PHONE = "789123";
	String ADDRESS = "456 Dallas Ave";
	String CITY = "Arlington";
	String STATE = "Texas";
	String ZIP = "785236";
	String CONTACTPASSWORD = "KKK@963";
	String CONFIRM_PASSWORD = "KKK@963";

	@BeforeSuite
	public void readConfig() {

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			System.out.println("This browser is not supportive!! ");
		}

		driver.manage().deleteAllCookies();
		//driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}

	//@Test(priority = 1)
	public void loginTest() {

		driver.findElement(usernameField).sendKeys(USERNAME);
		driver.findElement(passwordField).sendKeys(PASSWORD);
		driver.findElement(loginField).click();
		Assert.assertEquals(driver.findElement(dashboardField).getText(), DASHBOARD_HEADER_TEXT,
				"dashboard page not found!");

	}
	@Test(priority = 2)
	public void addCustomer() throws InterruptedException {
		loginTest();
		Thread.sleep(2000);
		driver.findElement(customerMenuField).click();
		driver.findElement(addCustomerField).click();
		waitForElement(driver, 5, addCustomer_HeaderField);
		Assert.assertEquals(driver.findElement(addCustomer_HeaderField).getText(), ADDCONTACT_HEADER_TEXT, "AddCustomer Page Not Found");

		driver.findElement(fullnameField).sendKeys(FULLNAME + randomNumGenerator(999));
		selectFromDropDown(driver.findElement(companyDropDownField), "Bank Of America");
		 
		  driver.findElement(emailField).sendKeys(randomNumGenerator(9999) + EMAIL);
		  driver.findElement(phoneField).sendKeys(PHONE + randomNumGenerator(99));
		  driver.findElement(addressField).sendKeys(ADDRESS);
		  driver.findElement(cityField).sendKeys(CITY);
		  driver.findElement(stateField).sendKeys(STATE);
		  driver.findElement(zipField).sendKeys(ZIP);
		  
		 selectFromDropDown(driver.findElement(countryField), "United States");
		 selectFromDropDown(driver.findElement(tagField), "IT Training");
		 selectFromDropDown(driver.findElement(currencyField), "USD");
		 selectFromDropDown(driver.findElement(groupField), "Java");
		 
		driver.findElement(passwordContactField).sendKeys(CONTACTPASSWORD);
		driver.findElement(confirmPassField).sendKeys(CONFIRM_PASSWORD); 
		driver.findElement(submitContact).click();
		
	}

	private void waitForElement(WebDriver driver, int waitTime, By elementTobeLocated) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementTobeLocated));
		
	}
 
	private int randomNumGenerator(int bound) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(bound);
		return generatedNum;
		
	}
	
	private void selectFromDropDown(WebElement element, String visibleText) {
		Select sel = new Select(element);
		 sel.selectByVisibleText(visibleText);
	}

	// @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
