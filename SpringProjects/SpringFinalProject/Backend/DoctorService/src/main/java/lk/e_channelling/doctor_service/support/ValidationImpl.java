package lk.e_channelling.doctor_service.support;

import lk.e_channelling.doctor_service.DoctorServiceApplication;
import lk.e_channelling.doctor_service.models.Doctor;
import org.springframework.stereotype.Component;

@Component
public class ValidationImpl implements Validation {


    @Override
    public boolean stringLengthValidator(String string, int length) {
        if (null != string) {
            if (string.length() == length)
                return true;
            return false;
        } else {
            return false;
        }

    }

    @Override
    public boolean stringMinLengthValidator(String string, int length) {

        if (null != string) {
            if (string.length() >= length)
                return true;
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean stringMaxLengthValidator(String string, int length) {
        if (null != string) {
            if (string.length() <= length)
                return true;
            return false;
        } else {
            return false;
        }

    }

    @Override
    public boolean intValidator(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean doctorSaveValidator(Doctor doctor) {
        if (stringMinLengthValidator(doctor.getName(), DoctorServiceApplication.NAME_MIN_LENGTH) && stringMinLengthValidator(doctor.getContact(), DoctorServiceApplication.CONTACT_MIN_LENGTH) && stringMaxLengthValidator(doctor.getContact(), DoctorServiceApplication.CONTACT_MAX_LENGTH))
            return true;
        return false;
    }
}
