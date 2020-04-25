package xyz.wickc.networkutils.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created on 2020/2/17
 *
 * @author wicks
 * @since 1.8
 */
public class DecodeUtils {
    public static byte[] deCodeGzipCode(byte[] bytes){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            GZIPInputStream inputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            IOUtils.copy(inputStream,outputStream);

            return outputStream.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] deCodeGzipCode(InputStream inputStream){
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            IOUtils.copy(gzipInputStream,outputStream);

            return outputStream.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解码 BR 压缩类型的数据!
     * @param bytes 原始压缩数据
     * @return 解码过后的数据
     */
    public static byte[] deCodeBrotliCode(byte[] bytes){
        return BrotliDecodeUtils.deCodeBrotliRespBody(bytes);
    }
}
