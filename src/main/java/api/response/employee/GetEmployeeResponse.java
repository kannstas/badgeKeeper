package api.response.employee;

import java.sql.Timestamp;
import java.util.UUID;

public record GetEmployeeResponse(UUID id,
                                  String position,
                                  String department,
                                  Timestamp createdAt,
                                  Timestamp updatedAt){
}