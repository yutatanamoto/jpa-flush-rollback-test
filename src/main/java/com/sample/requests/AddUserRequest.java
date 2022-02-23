package com.sample.requests;

import lombok.Value;

@Value
public class AddUserRequest {
    String firstName;
    String lastName;
}
