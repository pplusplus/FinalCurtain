package com.pplusplus.finalcurtain.http.request.body.multipart;

import com.pplusplus.finalcurtain.http.request.body.HttpBody;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class MultipartFormBody implements HttpBody {

    private static final String BOUNDARY = "**FINAL~CURTAIN~HTTP**";
    private static final String TWO_HYPHENS = "--";
    private static final String LINE_END = "\n\r";

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
