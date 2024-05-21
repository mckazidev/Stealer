package dev.kazi.stealer.utils.decrypt.cookie;

import java.io.File;
import java.util.Date;

public class Cookie {

    protected String name;
    protected String value;
    protected Date expires;
    protected String path;
    protected String domain;
    protected boolean secure;
    protected boolean httpOnly;
    protected File cookieStore;
    
    public Cookie(final String name, final String value, final Date expires, final String path, final String domain, final boolean secure, final boolean httpOnly, final File cookieStore) {
        this.name = name;
        this.value = value;
        this.expires = expires;
        this.path = path;
        this.domain = domain;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.cookieStore = cookieStore;
    }
    
    public String getName() {
        return this.name;
    }
}
