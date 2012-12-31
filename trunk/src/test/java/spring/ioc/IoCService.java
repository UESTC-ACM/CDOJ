package spring.ioc;

/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 12-12-31
 * Time: 下午11:57
 * To change this template use File | Settings | File Templates.
 */
public class IoCService {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void display() {
        System.out.println(getMessage());
    }
}
