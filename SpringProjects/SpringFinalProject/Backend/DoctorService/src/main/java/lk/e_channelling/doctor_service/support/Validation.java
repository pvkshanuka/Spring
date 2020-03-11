package lk.e_channelling.doctor_service.support;

import lk.e_channelling.doctor_service.models.Doctor;

public interface Validation {

    public boolean stringLengthValidator(String string,int length);
    public boolean stringMinLengthValidator(String string,int length);
    public boolean stringMaxLengthValidator(String string,int length);
    public boolean intValidator(String string);
    public boolean doctorSaveValidator(Doctor doctor);

}
