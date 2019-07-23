package TATest.FileOperations;

import TATest.Action;

import java.util.ArrayList;

public interface ConfigFile {
    ArrayList<Action> getActionsFromFile(String file);
}
