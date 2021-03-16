package org.jzl.io.impl;

import org.jzl.io.IBuffer;
import org.jzl.io.IByteString;
import org.jzl.lang.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ByteString implements IByteString {

    private final byte[] data;

    public ByteString(byte[] data) {
        this.data = data;
    }

    @Override
    public byte get(int index) {
        return data[index];
    }

    @Override
    public String string(Charset charset) {
        return new String(this.data, charset);
    }

    @Override
    public String hexString(String delimiter, boolean isUpper) {
        return StringUtils.toHexString(delimiter, this.data, isUpper);
    }

    @Override
    public String hexString(boolean isUpper) {
        return hexString(StringUtils.EMPTY, isUpper);
    }

    @Override
    public String hexString() {
        return hexString(StringUtils.EMPTY, false);
    }

    @Override
    public String utf8String() {
        return string(StandardCharsets.UTF_8);
    }

    @Override
    public String base64String() {
        return null;
    }

    @Override
    public String base64UrlString() {
        return null;
    }

    @Override
    public IByteString md5() {
        return digest("MD5");
    }

    @Override
    public IByteString sha1() {
        return digest("SHA-1");
    }

    @Override
    public IByteString sha256() {
        return digest("SHA-256");
    }

    @Override
    public IByteString sha512() {
        return digest("SHA-512");
    }

    @Override
    public IByteString digest(String algorithm) {
        try {
            return  IByteString.wrap(MessageDigest.getInstance(algorithm).digest(this.data));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IByteString mac(String algorithm, IByteString key) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.toByteArray(), algorithm));
            return IByteString.wrap(mac.doFinal(this.data));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IByteString macSha1(IByteString key) {
        return mac("HmacSHA1", key);
    }

    @Override
    public IByteString macSha256(IByteString key) {
        return mac("HmacSHA256", key);
    }

    @Override
    public IByteString macSha512(IByteString key) {
        return mac("HmacSHA512", key);
    }

    @Override
    public IByteString toAsciiLowercase() {
        int index = 0;
        int length = this.data.length;
        while (index < length) {
            byte b = this.data[index++];
            if (b < 'A' || b > 'Z') {
                continue;
            }
            byte[] bytes = toByteArray();
            bytes[index] = (byte) (b - ('A' - 'a'));
            while (index < length) {
                b = this.data[index++];
                if (b >= 'A' && b <= 'Z') {
                    bytes[index] = (byte) (b - ('A' - 'a'));
                }
            }
            return IByteString.wrap(bytes);
        }
        return this;
    }

    @Override
    public IByteString toAsciiUppercase() {
        int index = 0;
        int length = this.data.length;
        while (index < length) {
            byte b = this.data[index++];
            if (b < 'a' || b > 'z') {
                continue;
            }
            byte[] bytes = toByteArray();
            bytes[index] = (byte) (b - ('a' - 'A'));
            while (index < length) {
                b = this.data[index++];
                if (b >= 'a' && b <= 'z') {
                    bytes[index] = (byte) (b - ('a' - 'A'));
                }
            }
            return IByteString.wrap(bytes);
        }
        return this;
    }

    @Override
    public IByteString substring(int beginIndex, int endIndex) {
        return IByteString.wrap(toByteArray(beginIndex, endIndex));
    }

    @Override
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(this.data).asReadOnlyBuffer();
    }

    @Override
    public IByteString write(OutputStream out) throws IOException {
        return write(out, 0, size());
    }

    @Override
    public IByteString write(OutputStream out, int offset, int length) throws IOException {
        out.write(this.data, offset, length);
        return this;
    }

    @Override
    public IByteString write(IBuffer buffer) throws IOException {
        return write(buffer, 0, size());
    }

    @Override
    public IByteString write(IBuffer buffer, int offset, int length) throws IOException {
        buffer.write(this.data, offset, length);
        return this;
    }


    @Override
    public byte[] toByteArray() {
        return snapshot();
    }

    @Override
    public int size() {
        return data.length;
    }

    protected byte[] toByteArray(int beginIndex, int endIndex) {
        int length = Math.abs(endIndex - beginIndex);
        int offset = Math.min(beginIndex, endIndex);
        byte[] newBytes = new byte[length];
        System.arraycopy(this.data, offset, newBytes, 0, length);
        return newBytes;
    }

    protected byte[] snapshot() {
        byte[] newBytes = new byte[this.data.length];
        System.arraycopy(this.data, 0, newBytes, 0, newBytes.length);
        return newBytes;
    }

}
