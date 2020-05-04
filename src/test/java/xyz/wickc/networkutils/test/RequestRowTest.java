package xyz.wickc.networkutils.test;

import org.junit.Test;
import xyz.wickc.networkutils.domain.NetworkRequestData;
import xyz.wickc.networkutils.utils.RequestRowParsing;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * Created on 2020/5/5
 *
 * @author wicks
 * @since 1.8
 */
public class RequestRowTest{

    private static final String[] templateArrays = {
            "JDRequestTemplate.txt",
            "LoginRequestTemplate.txt"
    };

    @Test
    public void testTemplate() throws Exception{
        for (String templateFileName : templateArrays) {
            Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(templateFileName)));

            NetworkRequestData requestData = RequestRowParsing.parsingRowRequest(reader);

            System.out.println(requestData.getHeaderMap());
            System.out.println(requestData.getRequestMethod());
            System.out.println(requestData.getUrl());
            System.out.println(new String(requestData.getRequestBodyData()));
        }
    }
}
