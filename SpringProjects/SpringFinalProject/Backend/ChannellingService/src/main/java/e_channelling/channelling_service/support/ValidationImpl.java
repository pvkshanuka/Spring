package e_channelling.channelling_service.support;

import e_channelling.channelling_service.models.Channelling;
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

    @Override
    public boolean saveValidator(Channelling channelling) {
        System.out.println(channelling);
        if (null == channelling.getHospital() || null == channelling.getRoom() || null == channelling.getPrice() || null == channelling.getStartTime() || null == channelling.getEndTime() || null == channelling.getDay())
            return false;
        return true;
    }
}
