package lk.e_channelling.appointment_service.exceptions;

public class AppointmentException extends RuntimeException{
    public AppointmentException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
