package requests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class RequestBase {

    public static String getResponseValue(Response response, String key){
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(key);
    }

    public static int getIntegerResponseValue(Response response, String key){
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(key);
    }

    public static List<String> getReponseList(Response response, String key){
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(key);
    }
}
