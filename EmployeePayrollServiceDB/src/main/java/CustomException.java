public class CustomException extends Exception{

    enum ExceptionType {
        TYPE_CONNECTION, TYPE_RETRIEVAL, TYPE_UPDATE;
    }
    ExceptionType type;

    public CustomException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
