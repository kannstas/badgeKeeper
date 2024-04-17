package util.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.exception.MissingRequiredArgumentException;

import java.util.UUID;

public class ServletUtils {
    public static void addDefaultHeaders(HttpServletResponse resp) {
        resp.addHeader("Content-Type", "application/json");
    }

    public static UUID extractIdOrThrow(HttpServletRequest req, String paramName) {
        String idParam = req.getParameter(paramName);

        if (idParam == null) {
            throw new MissingRequiredArgumentException(paramName);
        }

        return UUID.fromString(idParam);
    }
}