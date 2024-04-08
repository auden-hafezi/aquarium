package persistence;

import org.json.JSONObject;

// Represents an object that can be written as a JSON object.
public interface Writable {

    // EFFECTS: returns this as a JSONObject
    JSONObject toJson();
}
