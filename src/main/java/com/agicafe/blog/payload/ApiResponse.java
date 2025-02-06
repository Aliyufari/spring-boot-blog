package com.agicafe.blog.payload;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ApiResponse<T> {
    private Boolean status;
    private String message;

    @JsonIgnore
    private final Map<String, Object> data = new HashMap<>();

    public ApiResponse(Boolean status, String message, String key, Object value) {
        this.status = status;
        this.message = message;

        if (key != null) {
            this.data.put(key, value);
        }
    }

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return data;  
    }
}