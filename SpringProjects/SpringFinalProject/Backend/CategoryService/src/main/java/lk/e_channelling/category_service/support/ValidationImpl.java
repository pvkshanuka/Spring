package lk.e_channelling.category_service.support;

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
    public boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
