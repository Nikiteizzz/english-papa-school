package Interfaces;

import org.json.simple.JSONObject;

public interface Model {
    void fromJsonString(String jsonString);
    JSONObject toJsonObject();
}
