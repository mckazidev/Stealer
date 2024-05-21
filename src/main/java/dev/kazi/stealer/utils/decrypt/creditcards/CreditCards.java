package dev.kazi.stealer.utils.decrypt.creditcards;

import java.io.File;
import java.util.Date;

public class CreditCards {

    protected String name;
    protected String value;
    protected Date expires;
    protected String path;
    protected String domain;
    protected boolean secure;
    protected boolean httpOnly;
    protected File ccStore;
    
    public CreditCards(final String name, final String value, final Date expires, final String path, final String domain, final boolean secure, final boolean httpOnly, final File ccStore) {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.path = path;
        this.domain = domain;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.ccStore = ccStore;
    }
    
    public String getName() {
        return this.name;
    }
}
