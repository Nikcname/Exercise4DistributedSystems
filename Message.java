import java.io.Serializable;

// Message that contains everything needed to invoke the method of the instance
public class Message implements Serializable{
    String methodName;
    Object[] paramValues;
    Object classInstance;
    public Message(String m, Object[] values, Object instance){
        methodName = m;
        paramValues = values;
        classInstance = instance;
    }
}