/**
 * 
 */
package qa.backbase.test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import qa.backbase.cd.base.TestBase;
import qa.backbase.cd.pages.EditPage;
import qa.backbase.cd.pages.HomePage;
import qa.backbase.cd.util.TestUtil;

/**
 * @author Rajyalakshmi G
 *
 */
public class EditPageTest extends TestBase {
	HomePage homepage;
	EditPage editpage;
	
	long noComps;
	String editdatasheetName = "EditComputer";
	String invaliddatasheetname="InvalidData";
	String deletedatasheetName = "DeleteComputer";

	@BeforeMethod
	public void setup() {
		browser_Initilization();
		homepage = new HomePage();
		noComps = homepage.get_no_of_computers();
	}

	//TC_CD_08:Verify fields displyed in Edit Computer page
	@Test(priority=1)
	public void verify_page_header() {
		editpage=homepage.default_navigate_to_edit_page();
		Assert.assertEquals(editpage.validate_page_header(), "Edit computer",
				"Edit computer page header is NOT displayed as expected");
	}

	//TC_CD_08:Verify fields displyed in Edit Computer page
	@Test(priority=2)
	public void verify_edit_page_fields() {
		editpage=homepage.default_navigate_to_edit_page();
		Assert.assertEquals(editpage.validate_computername_field(), true, "Computername field is NOT displayed");
		Assert.assertEquals(editpage.validate_introduced_date_field(), true, "Introduced date field is NOT displayed");
		Assert.assertEquals(editpage.validate_discontinued_date_field(), true,
				"Discontinued date field is NOT displayed");
		Assert.assertEquals(editpage.validate_company_field(), true, "Company field is NOT displayed");
		Assert.assertEquals(editpage.validate_savethiscomputer_button(), true,
				"Create this computer button is NOT displayed");
		Assert.assertEquals(editpage.validate_cancel_button(), true, "Cancel button is NOT displayed");
		Assert.assertEquals(editpage.validate_deletethiscomputer_button(), true,
				"Delete this computer button is NOT displayed");
	}
	
	@DataProvider
	public Object[][] edit_page_valid_testdata(){
		return TestUtil.getTestData(editdatasheetName);
	}

	//TC_CD_09:Verify user is able to EDIT the Computer name
	//TC_CD_10:Verify user is able to EDIT 'Introduced date', 'Discontinued date' and Company
	@Test(priority=3,dataProvider="edit_page_valid_testdata")
	public void verify_user_able_to_edit_computerdetails(String oldcomputername, String introdate, String discondate,
			String compname, String newcomputername) {
		editpage=homepage.navigate_to_edit_page(oldcomputername);
		homepage=editpage.edit_a_computerdetails(newcomputername, introdate, discondate, compname);
		String message = driver.findElement(By.xpath("//div[@class='alert-message warning']")).getText();
		Assert.assertEquals(message.contains("Computer " + newcomputername + " has been updated"), true,
				"Computer NOT updated");
		
	}
	
	@DataProvider
	public Object[][] editcomputer_invalid_testdata() {
		return TestUtil.getTestData(invaliddatasheetname);
	}

	//TC_CD_12:Verify user is NOT able to EDIT Computer details by providing invalid date formats to Introduced date or Discontinued date
	@Test(priority=4,dataProvider="editcomputer_invalid_testdata")
	public void verify_error_when_invalid_introduceddate_provided(String computername, String introdate,
			String discondate, String compname) {
		editpage=homepage.navigate_to_edit_page(computername);
		Assert.assertEquals(editpage.validate_error_when_invalid_introduceddate_provided(computername, introdate,
				discondate, compname), true, "NO error while providing Invalid Introduced date");
	}

	//TC_CD_12:Verify user is NOT able to EDIT Computer details by providing invalid date formats to Introduced date or Discontinued date
	@Test(priority=5,dataProvider="editcomputer_invalid_testdata")
	public void verify_error_when_invalid_discontinueddate_provided(String computername, String introdate,
			String discondate, String compname) {
		editpage=homepage.navigate_to_edit_page(computername);
		Assert.assertEquals(editpage.validate_error_when_invalid_discontinued_date_provided(computername,
				introdate, discondate, compname), true, "NO error while providing Invalid Introduced date");
	}

	//TC_CD_13:Verify changes are discareded by clicking on Cancel button in EDIT page
	@Test(priority=6)
	public void verify_cancel_button_functionality() {
		editpage=homepage.default_navigate_to_edit_page();
		homepage = editpage.validate_cancel_functionality("QA test", "2019-02-09", "2019-12-10", "RCA");
		Assert.assertEquals(homepage.validate_addComputer_button(), true, "User NOT navigated to Home page");
	}
	
	@DataProvider
	public Object[][] deletecomputer_testdata() {
		return TestUtil.getTestData(deletedatasheetName);
	}
	
	@Test(priority=7,dataProvider="deletecomputer_testdata")
	public void verify_deletethiscomputer_functionality(String computername){
		editpage=homepage.navigate_to_edit_page(computername);
		homepage=editpage.validate_deletethiscomputer_functionality();
		String message = driver.findElement(By.xpath("//div[@class='alert-message warning']")).getText();
		Assert.assertEquals(message.contains("Computer has been deleted"), true,
				"Computer NOT deleted");
		Assert.assertEquals(homepage.get_no_of_computers(), noComps-1,"Computers count not changed");
	}
	
	@AfterMethod
	public void teardown(){
		driver.close();
	}

}
