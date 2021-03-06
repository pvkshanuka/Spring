package lk.e_channelling.appointment_service.support;

import lk.e_channelling.appointment_service.models.Appointment;

public interface Validation {

    public boolean stringLengthValidator(String string, int length);
    public boolean stringMinLengthValidator(String string, int length);
    public boolean stringMaxLengthValidator(String string, int length);
    public boolean isInt(String string);
    public boolean saveValidator(Appointment appointment);

}
