package util.exception;

public class MissingRequiredArgumentException extends BusinessLogicException {

    public MissingRequiredArgumentException(String paramName) {
        super("В запросе отсутствует необходимый параметр: " + paramName);
    }
}