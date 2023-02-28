package com.example.mycalendar.network;

import com.example.mycalendar.model.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryListDeserializer implements JsonDeserializer<List<Category>> {
    @Override
    public List<Category> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Category> categories = new ArrayList<>();
        JsonArray categoriesArr = json.getAsJsonArray();
        for (JsonElement elem : categoriesArr) {
            JsonObject categoryObj = elem.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : categoryObj.entrySet()) {
                String categoryName = entry.getKey();
                JsonObject categoryInnerObj = entry.getValue().getAsJsonObject();
                Category category = context.deserialize(categoryInnerObj, Category.class);
                category.setName(categoryName);
                categories.add(category);
            }
        }
        return categories;
    }
}