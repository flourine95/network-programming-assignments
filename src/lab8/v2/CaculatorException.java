package lab8.v2;

public class CaculatorException extends Exception{
    public CaculatorException(String loiRoiHomie) {
        super(loiRoiHomie);
    }

    @Override
    public String getMessage() {
        return "day la loi";
    }
}
