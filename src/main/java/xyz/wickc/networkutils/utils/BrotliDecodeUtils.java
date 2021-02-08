package xyz.wickc.networkutils.utils;

import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;
import org.apache.commons.compress.compressors.brotli.BrotliUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger(BrotliDecodeUtils.class);

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
            logger.warn("解码 BR 数据时出现错误!");

            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }

            return new byte[0];
        }


        return outputStream.toByteArray();
    }
}
