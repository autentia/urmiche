package com.autentia.domain.urmiche;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class InputStreamUrlBulkAcquirer implements UrlBulkAcquirer {

    private final InputStream inputstream;

    public InputStreamUrlBulkAcquirer(InputStream inputstream) {
        if (inputstream == null) throw new IllegalArgumentException("InputStream cannot be null.");
        this.inputstream = inputstream;
    }

    @Override
    public List<String> acquire() throws IOException {
        final ArrayList<String> urls = new ArrayList<>();

        final BufferedReader in = new BufferedReader(new InputStreamReader(inputstream));
        String line;
        while ((line = in.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                urls.add(line);
            }
        }
        return urls;
    }
}
