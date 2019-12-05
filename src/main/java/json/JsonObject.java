package json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    private HashMap<String, Json> elements;

    public JsonObject(JsonPair... jsonPairs) {
        elements = new HashMap<String, Json>();
        Arrays.asList(jsonPairs).stream().forEach(x -> elements.put(x.key, x.value));
    }

    @Override
    public String toJson() {
        return "{" + jsonObjectBody() + "}";
    }

    public void add(JsonPair jsonPair) {
        elements.put(jsonPair.key, jsonPair.value);
    }

    public Json find(String name) {
        return elements.get(name);
    }

    public JsonObject projection(String... names) {
        JsonObject res = new JsonObject();
        Arrays.asList(names).stream().forEach(x -> res.add(new JsonPair(x, projectionEl(x))));
        return res;
    }

    private String jsonObjectBody() {
        String jsonStr = new String();

        Set<Map.Entry<String, Json>> elSet = elements.entrySet();
        Iterator<Map.Entry<String, Json>> iterator = elSet.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Json> mentry = iterator.next();
            System.out.println(mentry);
            jsonStr += "'" + mentry.getKey() + "'" + ": " + mentry.getValue().toJson() ;
            if (iterator.hasNext()) { jsonStr += ", "; }
        }

        return jsonStr;
    }

    private Json projectionEl(String name) {
        Json el = find(name);
        if (el == null) {
            el = new JsonNull();
        }

        return el;
    }

}
