package qa.backbase.test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import qa.backbase.cd.base.TestBase;
import qa.backbase.cd.pages.AddComputerPage;
import qa.backbase.cd.pages.HomePage;
import qa.backbase.cd.util.TestUtil;

/**
 * @author Rajyalakshmi G
 *
 */

public class AddComputerPageTest extends TestBase {
	HomePage homepage;
	AddComputerPage addcomputerpage;
	long noComps;
	String testdatasheetName = "AddComputer";
	String invaliddatasheetname="InvalidData";

	@BeforeMethod
	public void setup() {
		browser_Initilization();
		homepage = new HomePage();
		noComps = homepage.get_no_of_computers();
		addcomputerpage = homepage.validate_navigation_to_addcomputer_page();
	}

	//TC_CD_03:Verify Add a computer page fields
	@Test
	public void verify_page_title() {
		Assert.assertEquals(addcomputerpage.validate_page_header(), "Add a computer",
				"Add a computer page header is wrong");
	}

	//TC_CD_03:Verify Add a computer page fields
	@Test
	public void verify_addacomputer_page_fields() {
		Assert.assertEquals(addcomputerpage.validate_computername_field(), true, "Computername field is NOT displayed");
		Assert.assertEquals(addcomputerpage.validate_introduced_date_field(), true,
				"Introduced date field is NOT displayed");
		Assert.assertEquals(addcomputerpage.validate_discontinued_date_field(), true,
				"Discontinued date field is NOT displayed");
		Assert.assertEquals(addcomputerpage.validate_company_field(), true, "Company field is NOT displayed");
		Assert.assertEquals(addcomputerpage.validate_createthiscomputer_button(), true,
				"Create this computer button is NOT displayed");
		Assert.assertEquals(addcomputerpage.validate_cancel_button(), true, "Cancel button is NOT displayed");
	}

	@DataProvider
	public Object[][] addacomputer_valid_testdata() {
		return TestUtil.getTestData(testdatasheetName);
	}

	//TC_CD_04:Verify user is able to add a New computer
	//TC_CD_21:Verify user is able to add a Computer name with already existing name and other details
	@Test(dataProvider = "addacomputer_valid_testdata")
	public void verify_addacomputer_with_valid_data(String computername, String introdate, String discondate,
			String compname) {
		homepage = addcomputerpage.create_a_computer(computername, introdate, discondate, compname);
		String message = driver.findElement(By.xpath("//div[@class='alert-message warning']")).getText();
		Assert.assertEquals(message.contains("Computer " + computername + " has been created"), true,
				"Computer NOT added");
		Assert.assertEquals(homepage.get_no_of_computers(), noComps + 1,
				"Computers count is NOT updated on HomePage after adding a computer");
	}

	//TC_CD_06:Verify user is NOT able to add a computer without Computername
	@Test
	public void verify_error_when_computername_not_provided() {
		Assert.assertEquals(addcomputerpage.validate_error_when_computername_not_provided(), true,
				"NO error while Computer name field is NOT provided");
	}
	
	@DataProvider
	public Object[][] addacomputer_invalid_testdata() {
		return TestUtil.getTestData(invaliddatasheetname);
	}

	//TC_CD_07:Verify user is NOT able to add a computer with Invalid date format
	@Test(dataProvider="addacomputer_invalid_testdata")
	public void verify_error_when_invalid_introduceddate_provided(String computername, String introdate,
			String discondate, String compname) {
		Assert.assertEquals(addcomputerpage.validate_error_when_invalid_introduceddate_provided(computername, introdate,
				discondate, compname), true, "NO error while providing Invalid Introduced date");
	}

	//TC_CD_07:Verify user is NOT able to add a computer with Invalid date format
	@Test(dataProvider="addacomputer_invalid_testdata")
	public void verify_error_when_invalid_discontinueddate_provided(String computername, String introdate,
			String discondate, String compname) {
		Assert.assertEquals(addcomputerpage.validate_error_when_invalid_discontinued_date_provided(computername,
				introdate, discondate, compname), true, "NO error while providing Invalid Introduced date");
	}

	@Test
	public void verify_cancel_button_functionality() {
		homepage = addcomputerpage.validate_cancel_functionality("QA test", "2019-02-09", "2019-12-10", "RCA");
		Assert.assertEquals(homepage.validate_addComputer_button(), true, "User NOT navigated to Home page");
		Assert.assertEquals(homepage.get_no_of_computers(), noComps,
				"Computers count mismatch before and after cancel functionality");
	}

	@AfterMethod
	public void teardown() {
		driver.close();
	}

}
