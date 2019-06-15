package chatClient;

import java.io.Serializable;
import java.math.BigInteger;

public class KeySheet implements Serializable {
    private BigInteger rsa_public_variable;
    private BigInteger dh_key;

    public KeySheet(BigInteger rsa_public_variable) {
        this.rsa_public_variable = rsa_public_variable;
    }

    public BigInteger getRsa_public_variable() {
        return rsa_public_variable;
    }

    public BigInteger getDh_key() {
        return dh_key;
    }

    public void setDh_key(BigInteger dh_key) {
        this.dh_key = dh_key;
    }
}
