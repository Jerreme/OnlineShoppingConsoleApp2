package org.devi. models;

import org.devi.interfaces.Credential;

public record Admin(String username, String password) implements Credential {

}
