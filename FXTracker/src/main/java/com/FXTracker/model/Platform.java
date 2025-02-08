package com.FXTracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "platforms")
public class Platform {

    @Id
    private String id;
    @Field(name = "platform_name")
    private String platformName;
    @Field(name = "stocks")
    private List<String> stocks;
    @Field(name = "users_id")
    private List<String> users;
}
