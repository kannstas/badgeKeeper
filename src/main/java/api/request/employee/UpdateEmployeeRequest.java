package api.request.employee;

import java.sql.Timestamp;

public record UpdateEmployeeRequest(String position,
                                    String department,
                                    Timestamp createdAt,
                                    Timestamp updatedAt){
}