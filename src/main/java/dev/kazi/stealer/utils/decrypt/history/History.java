package dev.kazi.stealer.utils.decrypt.history;

import java.io.File;
import java.util.Date;

public class History {

    protected String name;
    protected String value;
    protected Date expires;
    protected String path;
    protected String domain;
    protected boolean secure;
    protected boolean httpOnly;
    protected File passwordStore;
    
    public History(final String name, final String value, final Date expires, final String path, final String domain, final boolean secure, final boolean httpOnly, final File passwordStore) {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.path = path;
        this.domain = domain;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.passwordStore = passwordStore;
    }
    
    public String getName() {
        return this.name;
    }
}
