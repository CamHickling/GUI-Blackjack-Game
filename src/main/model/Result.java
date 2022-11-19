package model;

import org.json.JSONArray;
import org.json.JSONObject;

public enum Result {
    TIE, WIN, LOSS;

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        String resultString = "";
        switch (this) {
            case TIE:
                resultString = "Tie";
                break;
            case WIN:
                resultString = "Win";
            case LOSS:
                resultString = "Loss";
        }
        json.put("result", resultString);
        return json;
    }
}
