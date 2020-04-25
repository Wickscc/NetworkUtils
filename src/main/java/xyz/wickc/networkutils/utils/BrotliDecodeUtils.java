package xyz.wickc.networkutils.utils;

import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;
import org.apache.commons.compress.compressors.brotli.BrotliUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created on 2020/2/20
 *
 * @author wicks
 * @since 1.8
 */
public class BrotliDecodeUtils {
    public static byte[] deCodeBrotliRespBody(byte[] data){
        BrotliCompressorInputStream inputStream = null;

        try {
            inputStream = new BrotliCompressorInputStream(new ByteArrayInputStream(data));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new String(data));
        }


        return outputStream.toByteArray();
    }
}
