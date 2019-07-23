package TATest.FileOperations;

import TATest.Action;
import TATest.Enums.Actions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XlsFile implements ConfigFile {
    private ArrayList<Action> actions = new ArrayList<>();

    @Override
    public ArrayList<Action> getActionsFromFile(String fileName) {
        InputStream in;
        HSSFWorkbook wb = null;
        try {
            in = new FileInputStream(fileName);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = null;
        if (wb != null) {
            sheet = wb.getSheetAt(0);
        }
        if (sheet != null) {
            for (Row row : sheet) {
                Action action = new Action();
                for (Cell cell : row) {
                    if (cell.getRowIndex() > 0) {
                        switch (cell.getColumnIndex()) {
                            case 0:
                                action.setActions(Actions.valueOf(cell.getStringCellValue().toLowerCase()));
                                break;
                            case 1:
                                action.setParameter(cell.getStringCellValue());
                                break;
                            case 2:
                                action.setValue(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (row.getRowNum() > 0) actions.add(action);
            }
        }
        return actions;
    }
}
