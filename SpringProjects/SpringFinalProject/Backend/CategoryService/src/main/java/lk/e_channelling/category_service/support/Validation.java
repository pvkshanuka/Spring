package lk.e_channelling.category_service.support;

public interface Validation {

    public boolean stringLengthValidator(String string, int length);
    public boolean stringMinLengthValidator(String string, int length);
    public boolean stringMaxLengthValidator(String string, int length);
    public boolean isInt(String string);

}
