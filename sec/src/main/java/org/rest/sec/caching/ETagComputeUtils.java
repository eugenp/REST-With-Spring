package org.rest.sec.caching;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ETagComputeUtils {

    private ETagComputeUtils() {
        throw new AssertionError();
    }

    // API

    public static byte[] serialize(final Object obj) throws IOException {
        byte[] byteArray = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream out = null;
        try {
            // These objects are closed in the finally.
            baos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(baos);
            out.writeObject(obj);
            byteArray = baos.toByteArray();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return byteArray;
    }

    public static String getMd5Digest(final byte[] bytes) {
        final MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 cryptographic algorithm is not available.", e);
        }
        final byte[] messageDigest = md.digest(bytes);
        final BigInteger number = new BigInteger(1, messageDigest);
        // prepend a zero to get a "proper" MD5 hash value
        final StringBuffer sb = new StringBuffer('0');
        sb.append(number.toString(16));
        return sb.toString();
    }

}