package org.rest.sec.caching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ETagResponseWrapper extends HttpServletResponseWrapper {
    private HttpServletResponse response = null;
    private ServletOutputStream stream = null;
    private PrintWriter writer = null;
    private OutputStream buffer = null;

    public ETagResponseWrapper(final HttpServletResponse responseToSet, final OutputStream bufferToSet) {
        super(responseToSet);
        response = responseToSet;
        buffer = bufferToSet;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (stream == null)
            stream = new ETagResponseStream(response, buffer);

        return stream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer == null)
            writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), "UTF-8"));

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        stream.flush();
    }
}