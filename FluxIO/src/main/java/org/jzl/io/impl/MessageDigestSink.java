package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.IByteString;
import org.jzl.io.ISegments;
import org.jzl.io.ISink;
import org.jzl.lang.util.ObjectUtils;

import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestSink extends ForwardingSink implements ISink {

    private final MessageDigest messageDigest;

    public MessageDigestSink(ISink sink, String algorithm) throws NoSuchAlgorithmException {
        super(sink);
        this.messageDigest = MessageDigest.getInstance(algorithm);
    }

    @Override
    public ISink write(IBuffer buffer, int byteCount) throws IOException {
        int hasLength = byteCount;
        ISegments segments = buffer.segments();
        Segment segment = segments.commonReadableSegment();
        try {
            while (hasLength > 0 && ObjectUtils.nonNull(segment)) {
                if (hasLength > segment.length()) {
                    messageDigest.digest(segment.bytes, segment.position, segment.length());
                    hasLength -= segment.length();
                } else {
                    messageDigest.digest(segment.bytes, segment.position, hasLength);
                    hasLength = 0;
                }
                segment = segment.next;
            }
        } catch (DigestException ignored) {
        }
        return super.write(buffer, byteCount);
    }

    public IByteString hash(){
        return IByteString.wrap(messageDigest.digest());
    }

    public static MessageDigestSink of(ISink sink, String algorithm) throws NoSuchAlgorithmException {
        return new MessageDigestSink(sink, algorithm);
    }

    public static MessageDigestSink md5(ISink sink) throws NoSuchAlgorithmException {
        return of(sink, "MD5");
    }

    public static MessageDigestSink sha1(ISink sink) throws NoSuchAlgorithmException {
        return of(sink, "sha-1");
    }

    public static MessageDigestSink sha256(ISink sink) throws NoSuchAlgorithmException {
        return of(sink, "SHA-256");
    }

    public static MessageDigestSink sha512(ISink sink) throws NoSuchAlgorithmException {
        return of(sink, "SHA-512");
    }

}
