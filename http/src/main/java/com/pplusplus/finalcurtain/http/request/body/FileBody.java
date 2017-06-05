package com.pplusplus.finalcurtain.http.request.body;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Pat Powell on 02/06/2017.
 */
public class FileBody implements HttpBody {

    private File file;

    @Override
    public byte[] toByteArray() {
        try {
            DataOutputStream daos = new DataOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
