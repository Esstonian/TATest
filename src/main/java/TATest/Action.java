package TATest;

import TATest.Enums.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Action {
    private Actions actions;
    private String typeOfAction;    //Gson compatibility
    private String parameter;
    private String value;
    private String description;     //Gson compatibility
    private WebDriver driver;
    private Logger logger;
    private boolean result = false;

    public Action() {
        logger = Logger.getLogger("Action.class");
    }

    public Action(Actions actions) {
        this();
        this.actions = actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLogger() {   //Gson compatibility
        this.logger = Logger.getLogger("Action.class");
    }

    private String getParameter() {
        return parameter;
    }

    private String getValue() {
        return value;
    }

    Actions getActions() {
        return actions;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    private boolean isResult() {
        return result;
    }

    @Override
    public String toString() {
        return isResult() + " "
                + (actions != null? actions.toString(): "")
                + " " + (parameter != null? parameter: "")
                + " " + (value != null? value: "");
    }

    void doAction(WebDriver driver){
        this.driver = driver;
        switch (actions) {
            case openurl:
                openUrl();
                break;
            case click:
                click();
                break;
            case setvalue:
                setValue();
                break;
            case checkelementvisible:
                checkElementVisible();
                break;
            case screenshot:
                screenShot();
                break;
            default:
                break;
        }
    }

    private void openUrl() {
        try {
            driver.get(getParameter());
            driver.manage().window().maximize();
            logger.log(Level.INFO, "openurl " + getParameter());
            result = true;
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private void click() {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            driver.findElement(By.xpath(getParameter())).click();
            logger.log(Level.INFO, "click " + getParameter());
            result = true;
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.ElementNotInteractableException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private void setValue() {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            driver.findElement(By.xpath(getParameter())).sendKeys(getValue());
            logger.log(Level.INFO, "setvalue " + getValue());
            result = true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private void checkElementVisible() {
        try {
            boolean answer =  driver.findElement(By.xpath(getParameter())).isDisplayed();
            logger.log(Level.INFO, getParameter() + (answer? " visible": " not visible"));
            result = true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private void screenShot() {
        byte[] screen = ((TakesScreenshot) new Augmenter().augment(driver)).getScreenshotAs(OutputType.BYTES);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-SS");
        String date = dateFormat.format(new Date());
        try (FileOutputStream fos = new FileOutputStream(date + ".png")) {
            fos.write(screen);
            logger.log(Level.INFO, "screenshot named " + date);
            result = true;
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
