package api.request.employee;

import api.common.Position;

public record CreateEmployeeRequest(Position position,
                                    String department) {
}