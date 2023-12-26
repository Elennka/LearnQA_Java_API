package lib;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    public static String getWrongRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "example.com";
    }

    public static String getRandomUsername(int count) {
        String username = RandomStringUtils.randomAlphanumeric(count);
        return username;
    }

    public static Map<String, String> getRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return data;
    }

    public static Map<String, String> getRegistrationData(String parameters) {
        Map<String, String> userData = new HashMap<>();
        String[] parts = parameters.split(",");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("email")) userData.put(parts[i], DataGenerator.getRandomEmail());
            if (parts[i].equals("password")) userData.put(parts[i], "123");
            if (parts[i].equals("username")) userData.put(parts[i], "learnqa");
            if (parts[i].equals("firstName")) userData.put(parts[i], "learnqa");
            if (parts[i].equals("lastName")) userData.put(parts[i], "learnqa");
        }

        return userData;
    }

    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = DataGenerator.getRegistrationData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }

}
