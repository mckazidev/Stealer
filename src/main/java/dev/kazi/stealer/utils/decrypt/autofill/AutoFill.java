package dev.kazi.stealer.utils.decrypt.autofill;

import java.io.File;
import java.util.Date;

public class AutoFill {

    protected String name;
    protected String value;
    protected Date expires;
    protected String path;
    protected String domain;
    protected boolean secure;
    protected boolean httpOnly;
    protected File autoFillStore;
    
    public AutoFill(final String name, final String value, final Date expires, final String path, final String domain, final boolean secure, final boolean httpOnly, final File autoFillStore) {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.path = path;
        this.domain = domain;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.autoFillStore = autoFillStore;
    }
    
    public String getName() {
        return this.name;
    }
}
