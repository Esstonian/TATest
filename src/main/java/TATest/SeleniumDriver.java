package TATest;

import TATest.Enums.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class SeleniumDriver {
    private ArrayList<Action> actions;
    private static WebDriver driver;

    SeleniumDriver(ArrayList<Action> actions) {
        this.actions = actions;
        Logger logger = Logger.getLogger("SeleniumDriver.class");
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        if (actions.get(0).getActions() != Actions.openurl)
            logger.log(Level.WARNING, "First action is not openurl");
    }

    ArrayList<Action> runActions() {
        actions.forEach(action -> action.doAction(driver));
        driver.close();
        return actions;
    }


}
