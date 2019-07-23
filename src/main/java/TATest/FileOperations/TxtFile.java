package TATest.FileOperations;

import TATest.Action;
import TATest.Enums.Actions;

import java.util.ArrayList;

public class TxtFile implements ConfigFile {

    @Override
    public ArrayList<Action> getActionsFromFile(String textFile) {
        String[] lines = textFile.split("\n");
        ArrayList<Action> actionList = new ArrayList<>();
        for (String line : lines) {
            String[] elements = line.trim().split("\\s");
            ArrayList<String> parameterList = new ArrayList<>();
            for (String element : elements) {
                if (element.trim().length() > 0) parameterList.add(element.toLowerCase());
            }
            if (parameterList.get(0).equals(Actions.openurl.toString().toLowerCase())
                    || parameterList.get(0).equals(Actions.click.toString().toLowerCase())
                    || parameterList.get(0).equals(Actions.checkelementvisible.toString().toLowerCase())) {
                Action action = new Action(Actions.valueOf(parameterList.get(0)));
                action.setParameter(parameterList.get(1));
                actionList.add(action);
            } else if (parameterList.get(0).equals(Actions.setvalue.toString().toLowerCase())) {
                Action action = new Action(Actions.valueOf(parameterList.get(0)));
                action.setParameter(parameterList.get(1));
                action.setValue(parameterList.get(3));
                actionList.add(action);
            } else if (parameterList.get(0).equals(Actions.screenshot.toString().toLowerCase())) {
                Action action = new Action(Actions.valueOf(parameterList.get(0)));
                actionList.add(action);
            }
        }
        return actionList;
    }
}
