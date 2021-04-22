package org.dawnn.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a user, with a location and a hwid.
 */
@Data
@Document(collection = "users")
public class User {

    private final String HWID;
    private final Location location;
    @Id
    public String id;

    @JsonCreator
    public User(@NonNull @JsonProperty("hwid") String hwid,
                @NonNull @JsonProperty("location") Location location) {
        this.HWID = hwid;
        this.location = location;
    }

}

