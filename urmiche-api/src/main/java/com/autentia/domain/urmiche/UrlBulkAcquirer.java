package com.autentia.domain.urmiche;

import java.io.IOException;
import java.util.List;

interface UrlBulkAcquirer {

    List<String> acquire() throws IOException;

}
