package com.task.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Athul KS
 * Represents a notification for the user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String message;
    private String orderId;
    private String date; 

}