package polytech.g6.blog.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String MEDECIN = "ROLE_MÉDÉCIN";

    public static final String AIDE_SOIGNANT = "ROLE_AIDE_SOIGNANT";

    private AuthoritiesConstants() {}
}
