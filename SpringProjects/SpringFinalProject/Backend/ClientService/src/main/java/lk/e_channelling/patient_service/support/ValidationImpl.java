package lk.e_channelling.patient_service.support;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lk.e_channelling.patient_service.ClientServiceApplication;
import lk.e_channelling.patient_service.models.Client;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationImpl implements Validation {

    public Pattern patternContact = Pattern.compile("(^\\d{9,10}$)");
    public Pattern patternEmail = Pattern.compile("^[a-zA-Z0-9_.]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.]+$");
    public Pattern patternPassword = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&].{6,15}$");


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
    public boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveValidator(Client client) {
        if (null != client.getName() && null != client.getAge() && null != client.getContact() && null != client.getEmail() && null != client.getType()) {
            return stringMinLengthValidator(client.getName(), ClientServiceApplication.CLIENT_NAME_MIN_LENGTH) && patternContact.matcher(client.getContact()).find() && patternEmail.matcher(client.getEmail()).find();
        }
        return false;
    }

    @Override
    public boolean updateValidator(Client client) {
        if (null != client.getId() && null != client.getName() && null != client.getAge() && null != client.getContact()) {
            return stringMinLengthValidator(client.getName(), ClientServiceApplication.CLIENT_NAME_MIN_LENGTH) && patternContact.matcher(client.getContact()).find();
        }
        return false;
    }

    @Override
    public boolean updatePwValidator(Client client) {
        if (null != client.getId() && null != client.getPassword()) {
            return patternPassword.matcher(client.getPassword()).find();

        }
        return false;
    }

}
