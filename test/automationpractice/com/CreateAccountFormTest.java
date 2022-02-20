package automationpractice.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import automationpractice.com.pageObject.Account;
import automationpractice.com.pageObject.CreateAccount;
import automationpractice.com.pageObject.CreateAccountForm;
import automationpractice.com.pageObject.Homepage;
import automationpractice.com.pageObject.SignInForm;
import utils.EmailsGenerator;

public class CreateAccountFormTest {

	private WebDriver driver;

	private Homepage homepage;
	private CreateAccount createAccount;
	private CreateAccountForm createAccountForm;
	private SignInForm signin;
	private Account account;

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();

		homepage = new Homepage(driver);
		createAccount = new CreateAccount(driver);
		createAccountForm = new CreateAccountForm(driver);
		signin = new SignInForm(driver);
		account = new Account(driver);

		String baseUrl = "http://automationpractice.com/index.php";
		driver.manage().window().maximize();
		driver.get(baseUrl);
	}

	@AfterClass
	public void closeAll() {
		account.getAccountLogout().click();
		driver.quit();
	}

	@Test(priority = 1)
	public void authenticationPage() {
		homepage.getSignInBtn().click();

		Assert.assertTrue(createAccount.getCreateAccountForm().isDisplayed());
		Assert.assertTrue(createAccount.getCreatAccountEmailField().isDisplayed());
		Assert.assertTrue(createAccount.getCreateAccountBtn().isDisplayed());
		Assert.assertTrue(signin.getSignInForm().isDisplayed());
	}

	@Test(priority = 2)
	public void authenticationPageEmailField() {
		
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailErrorMessage().isDisplayed());

		createAccount.setCreateAccountEmailField("samiya");
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailErrorMessage().isDisplayed());
		Assert.assertTrue(createAccount.getEmailFieldHighlightedRed().isDisplayed());

		createAccount.setCreateAccountEmailField("email@email.email");
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailBeenRegistered().isDisplayed());

		createAccount.setCreateAccountEmailField("samiya123@gmail.com");
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
	}

	@Test(priority = 3)
	public void personalInfoFields() {

		createAccountForm.setCustomerFirstNameField("Samiya");
		createAccountForm.setCustomerLastNameField("Nasir");
		createAccountForm.setCustomerEmailField("samiya@gmail.com");
		createAccountForm.setCustomerPasswordField("12345");

		createAccountForm.getAccountCreationForm().click();

		Assert.assertTrue(createAccountForm.getFirstNameHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordHighlightedGreen().isDisplayed());

		createAccountForm.setCustomerFirstNameField("");
		createAccountForm.setCustomerLastNameField("");
		createAccountForm.setCustomerEmailField("");
		createAccountForm.setCustomerPasswordField("");

		createAccountForm.getAccountCreationForm().click();

		Assert.assertTrue(createAccountForm.getFirstNameHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordHighlightedRed().isDisplayed());
	}

	@Test(priority = 4)
	public void requiredFieldsEmpty() {
		createAccountForm.getAddressAliasField().clear();
		createAccountForm.setCustomerEmailField("");
		createAccountForm.selectCountry("-");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getPhoneNumberError().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameError().isDisplayed());
		Assert.assertTrue(createAccountForm.getFirstNameError().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCountryRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getAddressRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getAddressAliasRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCityRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCountryUnselectedError().isDisplayed());

		createAccountForm.selectCountry("United States");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getStateRequredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
	}

	@Test(priority = 5)
	public void requiredFieldsInputFormat() throws Exception {

		createAccountForm.setCustomerEmailField("samiya@gmail");
		createAccountForm.setCustomerPasswordField("1234");
		createAccountForm.setPostalCodeField("qwer");
		createAccountForm.setHomePhoneField("ssss");
		createAccountForm.setMobilePhoneField("sdf");

		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getEmailInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
		Assert.assertTrue(createAccountForm.getHomePhoneInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getMobilePhoneInvalidError().isDisplayed());

		createAccountForm.setCustomerEmailField("email1@gmail.com");
		createAccountForm.setCustomerPasswordField("12345");
		createAccountForm.setPostalCodeField("1234");
		createAccountForm.setHomePhoneField("1234");
		createAccountForm.setMobilePhoneField("1234");

		Assert.assertTrue(createAccountForm.getEmailInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
		Assert.assertTrue(createAccountForm.getHomePhoneInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getMobilePhoneInvalidError().isDisplayed());
	}

	@Test(priority = 6)
	public void createAccountSuccessfully() {

		createAccountForm.setCustomerFirstNameField("Samiya");
		createAccountForm.setCustomerLastNameField("Nasir");
		createAccountForm.setCustomerPasswordField("12345");
		createAccountForm.selectCustomerDateOfBirthDay("9");
		createAccountForm.selectCustomerDateOfBirthMonth("04");
		createAccountForm.selectCustomerDateOfBirthYear("1999");
		createAccountForm.setAddressField("Centar");
		createAccountForm.setCityField("jhdch");
		createAccountForm.selectState("7");
		createAccountForm.setPostalCodeField("12345");
		createAccountForm.setHomePhoneField("12345");
		createAccountForm.setMobilePhoneField("12345");
		createAccountForm.setAddressAliasField("My Address");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getEmailBeenRegistered().isDisplayed());

		createAccountForm.setCustomerEmailField(EmailsGenerator.getNextEmail());
		createAccountForm.setCustomerPasswordField("12345");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.successfullyCreatedAccount().isDisplayed());
	}
}
