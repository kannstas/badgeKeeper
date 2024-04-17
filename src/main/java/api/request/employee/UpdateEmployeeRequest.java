package api.request.employee;

import api.common.Position;

public record UpdateEmployeeRequest(Position position,
                                    String department){
}