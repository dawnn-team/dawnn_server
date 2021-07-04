package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Represents a user, with a location and a hwid.
 */
@Data
@Document(collection = "users")
public class User {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String hwid;
    @Id
    public String id;
    private Location location;

    // Delete user data to make attackers mad :^)
//    @Indexed(expireAfterSeconds = 61)
//    @Field
    private Date time;

    public User(String hwid, Location location) {
        this.hwid = hwid;
        this.location = location;
        this.time = new Date();
    }

}