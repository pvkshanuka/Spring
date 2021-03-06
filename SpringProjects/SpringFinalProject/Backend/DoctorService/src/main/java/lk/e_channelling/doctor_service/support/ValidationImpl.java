package lk.e_channelling.doctor_service.support;

import lk.e_channelling.doctor_service.DoctorServiceApplication;
import lk.e_channelling.doctor_service.models.Doctor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationImpl implements Validation {

    public Pattern patternContact = Pattern.compile("(^\\d{9,10}$)");

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
        if (null == doctor.getName() || null == doctor.getContact()) {
            return false;
        } else {
            if (stringMinLengthValidator(doctor.getName(), DoctorServiceApplication.NAME_MIN_LENGTH) && patternContact.matcher(doctor.getContact()).find())
                return true;
            return false;
        }
    }
}
