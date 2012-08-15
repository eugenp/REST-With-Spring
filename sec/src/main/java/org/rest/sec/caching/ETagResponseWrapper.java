package org.rest.sec.caching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ETagResponseWrapper extends HttpServletResponseWrapper {
    private ServletOutputStream stream;
    private PrintWriter writer;
    private OutputStream buffer;

    public ETagResponseWrapper(final HttpServletResponse responseToSet, final OutputStream bufferToSet) {
        super(responseToSet);

        buffer = bufferToSet;
    }

    // API

    @Override
    public ServletOutputStream getOutputStream() {
        if (stream == null) {
            stream = new ETagResponseStream(buffer);
        }
        return stream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), "UTF-8"));
        }
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        stream.flush();
    }

}