package TATest.FileOperations;

import TATest.Action;
import TATest.Enums.Actions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class JsonFile implements ConfigFile {
    private ArrayList<Action> actions = new ArrayList<>();

    @Override
    public ArrayList<Action> getActionsFromFile(String textFile) {
        Type itemsMapType = new TypeToken<Map<Integer, Action>>() {
        }.getType();
        Map<Integer, Action> mapItems;
        mapItems = new Gson().fromJson(textFile, itemsMapType);
        mapItems.forEach((integer, action) -> {
            action.setActions(Actions.valueOf(action.getTypeOfAction().toLowerCase()));
            action.setLogger();
            actions.add(action);
        });
        return actions;
    }
}
