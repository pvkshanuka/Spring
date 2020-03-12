package lk.e_channeling.hospital_service.support;

import lk.e_channeling.hospital_service.HospitalServiceApplication;
import lk.e_channeling.hospital_service.models.Hospital;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationImpl implements Validation {

    public Pattern patternContact = Pattern.compile("(^\\d{9,10}$)");
    public Pattern patternEmail = Pattern.compile("^[a-zA-Z0-9_.]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.]+$");

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
    public boolean doctorSaveValidator(Hospital hospital) {
        if (null == hospital.getName() || null == hospital.getCity() || null == hospital.getEmail() || null == hospital.getContact()) {
            return false;
        } else {
            if (stringMinLengthValidator(hospital.getName(), HospitalServiceApplication.NAME_MIN_LENGTH) && patternContact.matcher(hospital.getContact()).find() && patternEmail.matcher(hospital.getEmail()).find())
                return true;
            return false;
        }
    }
}
