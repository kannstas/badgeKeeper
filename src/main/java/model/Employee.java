package model;

import java.sql.Timestamp;
import java.util.UUID;

public record  Employee (UUID id,
                         String position,
                         String department,
                         Timestamp createdAt,
                         Timestamp updatedAt) {
}