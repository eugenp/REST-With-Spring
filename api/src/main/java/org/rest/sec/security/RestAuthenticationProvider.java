package org.rest.sec.security;

import java.util.List;
import java.util.Set;

import org.rest.common.security.SpringSecurityPrincipal;
import org.rest.sec.client.template.auth.AuthenticationRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * - note: the original is DaoAuthenticationProvider <br/>
 * An {@link AuthenticationProvider} implementation that retrieves user details from a {@link UserDetailsService}.
 */
@Component
@Profile("client")
public class RestAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    @Qualifier("authenticationRestTemplate")
    private AuthenticationRestTemplate authenticationApi;

    // ~ Instance fields ================================================================================================

    private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();

    private SaltSource saltSource;

    public RestAuthenticationProvider() {
        super();
    }

    // ~ Methods ========================================================================================================

    @Override
    @SuppressWarnings("deprecation")
    protected void additionalAuthenticationChecks(final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;

        if (saltSource != null) {
            salt = saltSource.getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }

        final String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }
    }

    @Override
    protected final UserDetails retrieveUser(final String name, final UsernamePasswordAuthenticationToken authentication) {
        final String password = authentication.getCredentials().toString();

        UserDetails loadedUser = null;
        try {
            final ResponseEntity<User> authenticationResponse = authenticationApi.authenticate(name, password);
            if (authenticationResponse.getStatusCode().value() == 401) {
                // temporary - the idea here is to generate the not authorized exception - not by hand, but by returning wrong credentials which in turn will be refused later
                return new org.springframework.security.core.userdetails.User("wrongUsername", "wrongPass", Lists.<GrantedAuthority> newArrayList());
            }

            final User principalFromRest = authenticationResponse.getBody();

            final Set<String> privilegesFromRest = Sets.newHashSet();
            final Set<Role> roles = principalFromRest.getRoles();
            for (final Role role : roles) {
                privilegesFromRest.addAll(Collections2.transform(role.getPrivileges(), Functions.toStringFunction()));
            }
            final String[] authorityStringsAsArray = privilegesFromRest.toArray(new String[privilegesFromRest.size()]);
            final List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authorityStringsAsArray);

            loadedUser = new SpringSecurityPrincipal(name, password, true, authorities, "principalFromRest.getUuid()");
        } catch (final Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        return loadedUser;
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords. If not set, the password will be compared as plain text.
     * <p/>
     * For systems which are already using salted password which are encoded with a previous release, the encoder should be of type {@code org.springframework.security.authentication.encoding.PasswordEncoder}. Otherwise, the recommended
     * approach is to use {@code org.springframework.security.crypto.password.PasswordEncoder}.
     * 
     * @param passwordEncoderToSet
     *            must be an instance of one of the {@code PasswordEncoder} types.
     */
    public void setPasswordEncoder(final Object passwordEncoderToSet) {
        Assert.notNull(passwordEncoderToSet, "passwordEncoder cannot be null");

        if (passwordEncoderToSet instanceof PasswordEncoder) {
            passwordEncoder = (PasswordEncoder) passwordEncoderToSet;
            return;
        }

        if (passwordEncoderToSet instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder) passwordEncoderToSet;
            passwordEncoder = new PasswordEncoder() {
                @Override
                public String encodePassword(final String rawPass, final Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                @Override
                public boolean isPasswordValid(final String encPass, final String rawPass, final Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(final Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }
            };

            return;
        }

        throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * The source of salts to use when decoding passwords. <code>null</code> is a valid value, meaning the <code>DaoAuthenticationProvider</code> will present <code>null</code> to the relevant <code>PasswordEncoder</code>.
     * <p/>
     * Instead, it is recommended that you use an encoder which uses a random salt and combines it with the password field. This is the default approach taken in the {@code org.springframework.security.crypto.password} package.
     * 
     * @param saltSourceToSet
     *            to use when attempting to decode passwords via the <code>PasswordEncoder</code>
     */
    public void setSaltSource(final SaltSource saltSourceToSet) {
        saltSource = saltSourceToSet;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

}
