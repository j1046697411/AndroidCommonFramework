package org.jzl.io;

import org.jzl.io.impl.ByteString;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface IByteString {

    byte get(int index);

    String string(Charset charset);

    String hexString(String delimiter, boolean isUpper);

    String hexString(boolean isUpper);

    String hexString();

    String utf8String();

    String base64String();

    String base64UrlString();

    IByteString md5();

    IByteString sha1();

    IByteString sha256();

    IByteString sha512();

    IByteString digest(String algorithm);

    IByteString mac(String algorithm, IByteString key);

    IByteString macSha1(IByteString key);

    IByteString macSha256(IByteString key);

    IByteString macSha512(IByteString key);

    IByteString toAsciiLowercase();

    IByteString toAsciiUppercase();

    IByteString substring(int beginIndex, int endIndex);

    ByteBuffer asByteBuffer();

    IByteString write(OutputStream out) throws IOException;

    IByteString write(OutputStream out, int offset, int length) throws IOException;

    IByteString write(IBuffer buffer) throws IOException;

    IByteString write(IBuffer buffer , int offset, int length) throws IOException;

    byte[] toByteArray();

    int size();

    static IByteString wrap(byte[] data){
        return new ByteString(data);
    }

}
