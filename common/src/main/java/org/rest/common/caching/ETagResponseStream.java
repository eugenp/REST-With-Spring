package org.rest.common.caching;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class ETagResponseStream extends ServletOutputStream {
    private boolean closed = false;
    private OutputStream stream = null;

    public ETagResponseStream(final OutputStream streamToSet) {
        super();
        stream = streamToSet;
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            stream.close();
            closed = true;
        }
    }

    @Override
    public void flush() throws IOException {
        if (!closed) {
            stream.flush();
        }
    }

    @Override
    public void write(final int b) throws IOException {
        if (!closed) {
            stream.write((byte) b);
        }
    }

    @Override
    public void write(final byte b[], final int off, final int len) throws IOException {
        if (!closed) {
            stream.write(b, off, len);
        }
    }

    @Override
    public void write(final byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public boolean closed() {
        return closed;
    }

    public void reset() {
        // noop
    }

}