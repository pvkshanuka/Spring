package lk.e_channelling.patient_service.support;

import lk.e_channelling.patient_service.models.Client;

public interface Validation {

    public boolean stringLengthValidator(String string, int length);
    public boolean stringMinLengthValidator(String string, int length);
    public boolean stringMaxLengthValidator(String string, int length);
    public boolean isInt(String string);
    public boolean saveValidator(Client client);

}
