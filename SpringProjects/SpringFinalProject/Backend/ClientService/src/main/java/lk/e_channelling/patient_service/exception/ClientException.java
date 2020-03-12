package lk.e_channelling.patient_service.exception;

public class ClientException extends RuntimeException {
    public ClientException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
