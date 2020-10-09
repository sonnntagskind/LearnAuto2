package battlemetrics_rust.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static battlemetrics_rust.tests.BaseTest.getDriver;

public abstract class BasePage {

    WebDriver driver;
    Actions action;

    public BasePage() {
        this.driver = getDriver();
        PageFactory.initElements(driver, this);
        action = new Actions(driver);
    }

    public String parseWebElementToPlayerId(WebElement w) {
        return w.getAttribute("href").substring(38);
    }

    public String parseWebTimetoStringTime(WebElement webTime) {
        return (webTime.getAttribute("datetime").substring(0, 10).replace('-', '.')
                + " " + webTime.getAttribute("datetime").substring(11, 19)); }

    public static Date parseStringToDate(String stringTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy.MM.dd HH:mm:ss");
        return simpleDateFormat.parse(stringTime); }

    public Long parseStringTimeToLongTime(String stringTime) throws ParseException {
        return (parseStringToDate(stringTime)).getTime(); }

}

