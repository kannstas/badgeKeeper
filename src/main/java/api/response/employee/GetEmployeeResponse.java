package api.response.employee;

import api.common.Position;

import java.time.Instant;
import java.util.UUID;

public record GetEmployeeResponse(UUID id,
                                  Position position,
                                  String department,
                                  Instant createdAt,
                                  Instant updatedAt){
}