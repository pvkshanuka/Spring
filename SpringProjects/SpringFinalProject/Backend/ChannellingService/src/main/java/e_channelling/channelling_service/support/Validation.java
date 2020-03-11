package e_channelling.channelling_service.support;

import e_channelling.channelling_service.models.Channelling;

public interface Validation {

    public boolean stringLengthValidator(String string, int length);
    public boolean stringMinLengthValidator(String string, int length);
    public boolean stringMaxLengthValidator(String string, int length);
    public boolean isInt(String string);
    public boolean saveValidator(Channelling channelling);

}
