package org.rest.common.caching;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETagContentFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest servletRequest = (HttpServletRequest) req;
        final HttpServletResponse servletResponse = (HttpServletResponse) res;

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ETagResponseWrapper wrappedResponse = new ETagResponseWrapper(servletResponse, baos);
        chain.doFilter(servletRequest, wrappedResponse);

        final byte[] bytes = baos.toByteArray();

        final String token = '"' + ETagComputeUtils.getMd5Digest(bytes) + '"';
        servletResponse.setHeader("ETag", token); // always store the ETag in the header

        final String previousToken = servletRequest.getHeader("If-None-Match");
        if (previousToken != null && previousToken.equals(token)) { // compare previous token with the current one
            logger.debug("ETag match: returning 304 Not Modified");
            servletResponse.sendError(HttpServletResponse.SC_NOT_MODIFIED);
            servletResponse.setHeader("Last-Modified", servletRequest.getHeader("If-Modified-Since")); // re-use original last modified time
        } else {
            // first time through - set last modified time to now
            final Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 0);
            final Date lastModified = cal.getTime();
            servletResponse.setDateHeader("Last-Modified", lastModified.getTime());

            logger.debug("Writing body content");
            servletResponse.setContentLength(bytes.length);
            final ServletOutputStream sos = servletResponse.getOutputStream();
            sos.write(bytes);
            sos.flush();
            sos.close();
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        //
    }

    @Override
    public void destroy() {
        //
    }
}