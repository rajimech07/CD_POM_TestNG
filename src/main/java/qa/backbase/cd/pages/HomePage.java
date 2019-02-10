/**
 * 
 */
package qa.backbase.cd.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import qa.backbase.cd.base.TestBase;

/**
 * @author Rajyalakshmi G
 *
 */
public class HomePage extends TestBase{
	
	//OR
	@FindBy(id="searchsubmit")
	WebElement w_filterbyname;
	
	@FindBy(id="searchbox")
	WebElement w_inputbox;
	
	@FindBy(id="add")
	WebElement w_addcomputer;
	
	@FindBy(xpath="//section/h1")
	WebElement w_pageheader;
	
	@FindBy(xpath="//li/a[contains(text(),'Previous')]")
	WebElement w_pagination_previous;
	
	@FindBy(xpath="//li/a[contains(text(),'Next')]")
	WebElement w_pagination_next;
	
	@FindBy(xpath="//li[contains(@class,'current')]/a")
	WebElement w_pagination_middle;
	
	@FindBys(@FindBy(xpath="//table//tr/th"))
	List<WebElement> comp_table_columns;
	
	@FindBys(@FindBy(xpath="//table//tr"))
	List<WebElement> comp_table_rows;
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}
	
	public String validate_page_header(){
		return w_pageheader.getText();
	}
	
	public long get_no_of_computers(){
		String[] headertext=validate_page_header().split(" ");
		long nComps=Long.parseLong(headertext[0]);
		return nComps;
	}
	
	public boolean validate_filterbyname_button(){
		return w_filterbyname.isDisplayed();
	}
	
	public boolean validate_filterbyname_textbox(){
		return w_inputbox.isDisplayed();
	}
	
	public boolean validate_addComputer_button(){
		return w_addcomputer.isDisplayed();
	}
	
	public boolean validate_pagination_previous(){
		return w_pagination_previous.isDisplayed();
	}
	
	public boolean validate_pagination_next(){
		return w_pagination_next.isDisplayed();
	}
	
	public String validate_pagination_text(){
		return w_pagination_middle.getText();
	}
	
	public AddComputerPage validate_navigation_to_addcomputer_page(){
		w_addcomputer.click();
		return new AddComputerPage();
	}
	
	public String[] validate_computersdata_table_columns(){
		String[] colnames=new String[comp_table_columns.size()];
		for(int i=0;i<comp_table_columns.size();i++){
			colnames[i]=comp_table_columns.get(i).getText();
		}
		
		return colnames;
	}
	
	public Integer validate_computersdata_table_rows(){
		return comp_table_rows.size();
	}
	
	
	public Map<Integer,WebElement> validate_filterbyname_functionality(String computername){
		w_inputbox.sendKeys(computername);
		w_filterbyname.click();
		int noResults=validate_computersdata_table_rows();
		Map<Integer,WebElement> data=new HashMap<Integer,WebElement>();
		for(int i=1;i<noResults;i++){
			String xpath="//table//tr["+i+"]/td/a";
			WebElement rowdata=driver.findElement(By.xpath(xpath));
			data.put(i, rowdata);
		}
		
		return data;
	}
	
	public Map<Integer,List<WebElement>> validate_filterbyname_data(String computername){
		w_inputbox.sendKeys(computername);
		w_filterbyname.click();
		int noResults=validate_computersdata_table_rows();
		Map<Integer,List<WebElement>> data=new HashMap<Integer,List<WebElement>>();
		for(int i=1;i<noResults;i++){
			String xpath="//table//tr["+i+"]/td";
			List<WebElement> rowdata=driver.findElements(By.xpath(xpath));
			data.put(i, rowdata);
		}
		
		return data;
	}
	
	public EditPage navigate_to_edit_page(String computername){
		Map<Integer,WebElement> filterResult=validate_filterbyname_functionality(computername);
		filterResult.get(1).click();
		
		return new EditPage();
	}
	
	public EditPage default_navigate_to_edit_page(){
		driver.findElement(By.xpath("//table//tr[1]/td/a")).click();
		return new EditPage();
	}

}
