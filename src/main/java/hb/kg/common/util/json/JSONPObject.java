package hb.kg.common.util.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hb.kg.common.util.json.serializer.JSONSerializable;
import hb.kg.common.util.json.serializer.JSONSerializer;
import hb.kg.common.util.json.serializer.SerializeWriter;
import hb.kg.common.util.json.serializer.SerializerFeature;

public class JSONPObject implements JSONSerializable {
    public static String SECURITY_PREFIX = "/**/";
    private String function;
    private final List<Object> parameters = new ArrayList<Object>();

    public JSONPObject() {}

    public JSONPObject(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void addParameter(Object parameter) {
        this.parameters.add(parameter);
    }

    public String toJSONString() {
        return toString();
    }

    public void write(JSONSerializer serializer,
                      Object fieldName,
                      Type fieldType,
                      int features)
            throws IOException {
        SerializeWriter writer = serializer.out;
        if ((features & SerializerFeature.BrowserSecure.mask) != 0
                || (writer.isEnabled(SerializerFeature.BrowserSecure.mask))) {
            writer.write(SECURITY_PREFIX);
        }
        writer.write(function);
        writer.write('(');
        for (int i = 0; i < parameters.size(); ++i) {
            if (i != 0) {
                writer.write(',');
            }
            serializer.write(parameters.get(i));
        }
        writer.write(')');
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
