/**
 * 
 */
package qa.backbase.test;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class HomePageTest extends TestBase {

	HomePage homepage;
	AddComputerPage addcomputerpage;

	String filterbynamesheet = "FilterByName";
	String[] expectedcolnames = { "Computer name", "Introduced", "Discontinued", "Company" };

	@BeforeMethod
	public void setup() {
		browser_Initilization();
		homepage = new HomePage();
	}

	//TC_CD_01:Verify Landing page fields
	@Test(priority = 1)
	public void verify_home_page_header() {
		String header = homepage.validate_page_header();
		Assert.assertEquals(header.contains("computers found"), true);
	}

	//TC_CD_01:Verify Landing page fields
	@Test(priority = 2)
	public void verify_homepage_fields() {
		Assert.assertEquals(homepage.validate_filterbyname_button(), true, "Filter by name button NOT displayed");
		Assert.assertEquals(homepage.validate_filterbyname_textbox(), true, "Filter by name inputbox NOT displayed");
		Assert.assertEquals(homepage.validate_addComputer_button(), true, "Add a computer button NOT displayed");
	}

	//TC_CD_02:Verify Computers data displayed in landing page
	@Test(priority = 3)
	public void verify_pagination_fields() {
		Assert.assertEquals(homepage.validate_pagination_previous(), true,
				"Previous button of Pagination bar is NOT displayed");
		Assert.assertEquals(homepage.validate_pagination_next(), true,
				"Next button of Pagination bar is NOT displayed");
		String pagi_text = homepage.validate_pagination_text();
		Assert.assertEquals(pagi_text.contains("Displaying 1 to 10 of"), true,
				"Pagination text is NOT displayed as expected");
	}

	//TC_CD_02:Verify Computers data displayed in landing page
	@Test(priority = 4)
	public void verify_computersdata_table_columns() {
		String[] actualcolnames = homepage.validate_computersdata_table_columns();
		Assert.assertEquals(actualcolnames.length, 4, "Computers data table is NOT displayed with 4 columns");
		Assert.assertEquals(homepage.validate_computersdata_table_rows()-1, 10,"By default Computers data table is NOT displayed with 10 records");
		for (int i = 0; i < expectedcolnames.length; i++) {
			while (expectedcolnames[i].equalsIgnoreCase(actualcolnames[i]) == false)
				Assert.assertEquals(expectedcolnames[i].equalsIgnoreCase(actualcolnames[i]), true,
						expectedcolnames[i] + " not displayed");
		}
	}

	@DataProvider
	public Object[][] filterbyname_test_data() {
		return TestUtil.getTestData(filterbynamesheet);
	}

	//TC_CD_14:Verify user is able to Filter by Partial text of Computer name
	//TC_CD_15:Verify user is able to Filter by Single character as Computer name
	//TC_CD_17:Verify 'Filter by name' functionality when there are no matches of Computer name
	@Test(priority=5,dataProvider = "filterbyname_test_data")
	public void verify_filterbyname_functionality(String computername) {
		Map<Integer, WebElement> filterResults = homepage.validate_filterbyname_functionality(computername);
		if (filterResults.size() > 0) {
			for (Map.Entry<Integer, WebElement> we : filterResults.entrySet()) {
				Assert.assertEquals(we.getValue().getText().toLowerCase().contains(computername.toLowerCase()), true,
						"Filter results are displayed with unmatched data");
			}

		} else {
			Assert.assertEquals(driver.findElement(By.xpath("//div[@class='well']/em")).getText(), "Nothing to display",
					"No message displayed when no Filter matches found");
		}

	}

	//TC_CD_18:Verify user is able to Delete the computer details
	@Test(priority=6)
	public void verify_navigation_to_addcomputer_page() {
		addcomputerpage = homepage.validate_navigation_to_addcomputer_page();
		Assert.assertEquals(addcomputerpage.validate_page_header(), "Add a computer",
				"User NOT navigated to Add a Computer page");
	}

	@AfterMethod
	public void teardown() {
		driver.close();
	}

}
