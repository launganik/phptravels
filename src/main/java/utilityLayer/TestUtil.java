package utilityLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;

import baseLayer.TestBase;

public class TestUtil extends TestBase {

	static Workbook book;
	static Sheet sheet;

	// Read data from external excel
	public static Object[][] getDataset(String fileName, String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(System.getProperty("user.dir") + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				if (sheet.getRow(i + 1).getCell(k).toString() != null) {
					data[i][k] = "" + sheet.getRow(i + 1).getCell(k).toString();
					// System.out.println(data[i][k]);
				}
			}
		}
		return data;
	}

	// Write data into external excel
	public static void writeData(String fileName, String sheetName, List<String> data) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XSSFWorkbook wb;
		try {
			wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);

			int cellNo = 0;
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);
			for (String s : data) {
				row.createCell(cellNo).setCellValue(s);
				cellNo++;
			}

			FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir") + fileName);
			wb.write(fout);
			fout.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ExtentReports extentReportSetup() {
		// Boolean flag represents replaceExisting?
		ExtentReports extent = new ExtentReports(prop.getProperty("extentReportsFile"), false);
		extent.addSystemInfo("Selenium Version", prop.getProperty("seleniumVersion"));
		extent.loadConfig(new File(prop.getProperty("extentConfigFile")));
		return extent;
	}

	public static String getScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File src = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + prop.getProperty("screenshotFolder") + "/" + dateName + ".png";

		File destination = new File(path);

		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
			e.printStackTrace();
		}
		return path;
	}

}
