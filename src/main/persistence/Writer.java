package persistence;

import model.Game;
import model.Round;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//this code is based on the Json Serialize Demo @ https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class Writer {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String target;

    // EFFECTS: constructs writer to write to target file
    public Writer(String target) {
        this.target = target;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(target));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of round to file
    public void write(Game game) {
        JSONObject json = game.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
