package edu.messanger.utils;

import com.google.gson.Gson;
import edu.messanger.model.Message;

import java.util.Objects;

public class Utils {
    public static Message jsonToMessage(String jsonMessage) {
        return new Gson().fromJson(jsonMessage, Message.class);
    }

    public static String messageToJson(Message message) {
        return new Gson().toJson(message);
    }

    public static boolean isBlankOrEmpty(String s) {
        return Objects.isNull(s) || s.isBlank() || s.isEmpty();
    }

    public static boolean isNotBlankOrEmpty(String s) {
        return !isBlankOrEmpty(s);
    }

}
