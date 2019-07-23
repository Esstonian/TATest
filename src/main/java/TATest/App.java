package TATest;

import TATest.Enums.Extension;
import TATest.FileOperations.JsonFile;
import TATest.FileOperations.TxtFile;
import TATest.FileOperations.XlsFile;
import TATest.FileOperations.XmlFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static Logger logger = Logger.getLogger("App.class");

    public static void main(String[] args) {
        if (args.length > 0) {
            processingTests(args[0]).forEach(System.out::println);
        } else {
            logger.log(Level.WARNING, "usage: java -jar App.jar task.file");
        }
    }

    static ArrayList<Action> processingTests(String fileName) {
        ArrayList<Action> actions = null;
        Extension extension = getExtension(fileName);
        String textFile = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            textFile = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.log(Level.WARNING, fileName + " open file error");
        }
        if (extension != null) {
            switch (extension) {
                case txt:
                    actions = new TxtFile().getActionsFromFile(textFile);
                    break;
                case xls:
                    actions = new XlsFile().getActionsFromFile(fileName);
                    break;
                case xml:
                    actions = new XmlFile().getActionsFromFile(textFile);
                    break;
                case json:
                    actions = new JsonFile().getActionsFromFile(textFile);
                    break;
                default:
                    break;
            }
            if (actions != null) {
                actions = new SeleniumDriver(actions).runActions();
            } else logger.log(Level.WARNING, "Action list is null");
        } else logger.log(Level.WARNING, "wrong file extension");
        return actions;
    }

    private static Extension getExtension(String arg) {
        int dot = arg.lastIndexOf(".");
        return (dot == -1) ? null : Extension.valueOf(arg.substring(dot + 1));
    }
}
