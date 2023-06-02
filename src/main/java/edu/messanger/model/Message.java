package edu.messanger.model;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString
public class Message {
    @NonNull private String name;
    @NonNull private String text;
}
