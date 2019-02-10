/**
 * 
 */
package qa.backbase.cd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import qa.backbase.cd.base.TestBase;

/**
 * @author Rajyalakshmi G
 *
 */
public class TestUtil extends TestBase {

	public static long page_load_time_out = 30;
	public static long implicit_time_out = 20;

	public static String FilePath = System.getProperty("user.dir")
			+ "/src/main/java/qa/backbase/cd/testdata/TestData.xlsx";
	static Workbook book;
	static Sheet sheet;

	public static Object[][] getTestData(String SheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(FilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(SheetName);

		Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}

		return data;

	}

	public static void takeScreenshotAtEndOfTest() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile,
					new File(System.getProperty("user.dir") + "\\Screenshots\\" + System.currentTimeMillis() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
