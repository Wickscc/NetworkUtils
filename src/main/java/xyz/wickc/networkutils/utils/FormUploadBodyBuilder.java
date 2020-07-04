package xyz.wickc.networkutils.utils;

import xyz.wickc.networkutils.domain.FormUploadNetworkRequestData;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2020/7/3
 *
 * @author wicks
 * @since 1.8
 */
public class FormUploadBodyBuilder {
    private static final String NEXT_LINE = "\r\n";

    /**
     * 分隔符前后都要一样,不然会报错!
     */
    private static final String PREFIX = "--";
    private static final String CHARSET = "UTF-8";
    private static final String KEY_REGEX = "&";

    public static byte[] builderRequestBody(FormUploadNetworkRequestData requestData) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<String, String> textFrom = requestData.getTextFrom();
        Set<String> textFromKeySet = textFrom.keySet();

        Map<String, byte[]> uploaderForm = requestData.getUploaderForm();
        Set<String> uploaderFormKeySet = uploaderForm.keySet();

        try {
            for (String key : uploaderFormKeySet) {
                String[] keyArrays = key.split(KEY_REGEX);
                String fileName = keyArrays[2];
                String contentType = keyArrays[1];
                String name = keyArrays[0];

                outputStream.write((PREFIX + requestData.getDelimiter() + NEXT_LINE).getBytes(CHARSET));
                outputStream.write(("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"" + NEXT_LINE).getBytes("UTF-8"));
                outputStream.write(("Content-Type: " + contentType + NEXT_LINE + NEXT_LINE).getBytes(CHARSET));
                outputStream.write(uploaderForm.get(key));
                outputStream.write((NEXT_LINE + NEXT_LINE).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (String key : textFromKeySet) {
                outputStream.write((PREFIX + requestData.getDelimiter() + NEXT_LINE).getBytes(CHARSET));
                outputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"" + NEXT_LINE + NEXT_LINE).getBytes(CHARSET));
                outputStream.write((textFrom.get(key) + NEXT_LINE).getBytes(CHARSET));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write((PREFIX + requestData.getDelimiter() + PREFIX + NEXT_LINE).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
