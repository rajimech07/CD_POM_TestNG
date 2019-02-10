/**
 * 
 */
package qa.backbase.cd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import qa.backbase.cd.base.TestBase;

/**
 * @author Rajyalakshmi G
 *
 */
public class EditPage extends TestBase{
	
	//OR
	@FindBy(xpath="//section/h1")
	WebElement w_pageheader;
	
	@FindBy(id="name")
	WebElement w_computername;
	
	@FindBy(id="introduced")
	WebElement w_introduceddate;
	
	@FindBy(id="discontinued")
	WebElement w_discontinueddate;
	
	@FindBy(id="company")
	WebElement w_company;
	
	@FindBy(xpath="//input[@value='Save this computer']")
	WebElement w_save_btn;
	
	@FindBy(xpath="//div/a[text()='Cancel']")
	WebElement w_cancel_btn;
	
	@FindBy(xpath="//input[@value='Delete this computer']")
	WebElement w_delete_btn;
	
	public EditPage(){
		PageFactory.initElements(driver, this);
	}
	
	public String validate_page_header(){
		return w_pageheader.getText();
	}
	
	public boolean validate_computername_field(){
		return w_computername.isDisplayed();
	}
	
	public boolean validate_introduced_date_field(){
		return w_introduceddate.isDisplayed();
	}
	
	public boolean validate_discontinued_date_field(){
		return w_discontinueddate.isDisplayed();
	}
	
	public boolean validate_company_field(){
		return w_company.isDisplayed();
	}
	
	public boolean validate_savethiscomputer_button(){
		return w_save_btn.isDisplayed();
	}
	
	public boolean validate_cancel_button(){
		return w_cancel_btn.isDisplayed();
	}
	
	public boolean validate_deletethiscomputer_button(){
		return w_delete_btn.isDisplayed();
	}
	
	public HomePage edit_a_computerdetails(String computername,String introdate,String discondate,String compname){
		w_computername.clear();
		w_computername.sendKeys(computername);
		w_introduceddate.clear();
		w_introduceddate.sendKeys(introdate);
		w_discontinueddate.clear();
		w_discontinueddate.sendKeys(discondate);
		Select company=new Select(w_company);
		company.selectByVisibleText(compname);
		w_save_btn.click();
		
		return new HomePage();
	}
	
	public HomePage validate_cancel_functionality(String computername,String introdate,String discondate,String compname){
		w_computername.clear();
		w_computername.sendKeys(computername);
		w_introduceddate.clear();
		w_introduceddate.sendKeys(introdate);
		w_discontinueddate.clear();
		w_discontinueddate.sendKeys(discondate);
		Select company=new Select(w_company);
		company.selectByVisibleText(compname);
		w_cancel_btn.click();
		
		return new HomePage();
		
	}
	
	public boolean validate_error_when_computername_not_provided(){
		w_computername.clear();
		w_save_btn.click();
		return driver.findElement(By.xpath("//div[@class='clearfix error']/label[text()='Computer name']")).isDisplayed();
	}
	
	public boolean validate_error_when_invalid_introduceddate_provided(String computername,String introdate,String discondate,String compname){
		w_computername.clear();
		w_computername.sendKeys(computername);
		w_introduceddate.clear();
		w_introduceddate.sendKeys(introdate);
		w_discontinueddate.clear();
		w_discontinueddate.sendKeys(discondate);
		Select company=new Select(w_company);
		company.selectByVisibleText(compname);
		w_save_btn.click();
		return driver.findElement(By.xpath("//div[@class='clearfix error']/label[text()='Introduced date']")).isDisplayed();
	}
	
	public boolean validate_error_when_invalid_discontinued_date_provided(String computername,String introdate,String discondate,String compname){
		w_computername.clear();
		w_computername.sendKeys(computername);
		w_introduceddate.clear();
		w_introduceddate.sendKeys(introdate);
		w_discontinueddate.clear();
		w_discontinueddate.sendKeys(discondate);
		Select company=new Select(w_company);
		company.selectByVisibleText(compname);
		w_save_btn.click();
		return driver.findElement(By.xpath("//div[@class='clearfix error']/label[text()='Discontinued date']")).isDisplayed();
	}
	
	public HomePage validate_deletethiscomputer_functionality(){
		w_delete_btn.click();
		
		return new HomePage();
	}

}
