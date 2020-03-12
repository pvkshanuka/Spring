package lk.e_channeling.hospital_service.support;

import lk.e_channeling.hospital_service.models.Hospital;

public interface Validation {

    public boolean stringLengthValidator(String string, int length);
    public boolean stringMinLengthValidator(String string, int length);
    public boolean stringMaxLengthValidator(String string, int length);
    public boolean intValidator(String string);
    public boolean doctorSaveValidator(Hospital hospital);

}
