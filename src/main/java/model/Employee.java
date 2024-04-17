package model;

import api.common.Position;

import java.time.Instant;
import java.util.UUID;

public record  Employee (UUID id,
                         Position position,
                         String department,
                         Instant createdAt,
                         Instant updatedAt) {
}