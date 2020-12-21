package com.comcast.conatcttest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.comcast.commonutils.ExcelUtility;
import com.comcast.commonutils.FileUtility;
import com.comcast.commonutils.JavaUtils;
import com.comcast.commonutils.WebDriverUTils;
import com.comcast.objectrepositorylib.ContactInfo;
import com.comcast.objectrepositorylib.Contacts;
import com.comcast.objectrepositorylib.CreateNewConatct;
import com.comcast.objectrepositorylib.Home;
import com.comcast.objectrepositorylib.Login;

public class Cretecontact_12 
{

	
	 @Test
	   public void createConatctWithteam() throws Throwable {
			WebDriverUTils wLib = new WebDriverUTils();
			FileUtility fLib = new FileUtility();
			ExcelUtility elib = new ExcelUtility();
			
			/*Common  Data*/
			String URL  = fLib.getPropertyKeyValue("url");
			String USERNAME  = fLib.getPropertyKeyValue("username");
			String PASSWORD  = fLib.getPropertyKeyValue("password");
			String BROWSER  = fLib.getPropertyKeyValue("browser");
			
			/*Test  Data*/
			String orgNAme = elib.getExcelData("Contact", "tc_01", "OrgName")+JavaUtils.getRanDomData() ;
			String orgIndustry = elib.getExcelData("Contact", "tc_01", "Industry");
			String orgType = elib.getExcelData("Contact", "tc_01", "Type");
	    	String orgRating = elib.getExcelData("Contact", "tc_01", "Rating");
			String contactLastNAme  = elib.getExcelData("Contact", "tc_11", "LastName")+JavaUtils.getRanDomData();
			

			//step 1 : login to application
			WebDriver driver ;
			 if(BROWSER.equals("chrome")) {
			    driver = new ChromeDriver();
			 }else if(BROWSER.equals("firefox")) {
				 driver = new FirefoxDriver();
			 }else if(BROWSER.equals("ie")) {
				 driver = new InternetExplorerDriver();
			 }else {
				 driver = new ChromeDriver(); 
			 }
			 
			 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.get(URL);
				
				/*step 1 : login to app*/
				Login lp = new Login(driver);
				lp.loginToApp(USERNAME, PASSWORD);
				
				//Home page 
				Home  hp=new Home(driver);
				
				 //step 2 : Navigate to Contacts
				wLib.waitForElemnetToBeClickable(driver , hp.getContactLnk());
				hp.getContactLnk().click();
				
				  //step 3 : Navigate to create new contact page
				Contacts cp = new Contacts(driver);
				cp.getCreateOrgImg().click();
			 
				//step 4 : Create new Contact With assigned to group & save info & confirmation msg should be displayed
				CreateNewConatct  cnc=new CreateNewConatct(driver);
				cnc.createContactwthgroup(contactLastNAme, 2);
				
				//verify account is created in conatct_information page
				ContactInfo ci=new ContactInfo(driver);
				 String msg = ci.getSuccessFullMSG().getText();
		        Assert.assertTrue(msg.contains(contactLastNAme));
				
			 
				//step 5 : logout
	             hp.logout();
			 
	   }
}
