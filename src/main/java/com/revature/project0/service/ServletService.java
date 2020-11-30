package com.revature.project0.service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class ServletService {

    /**
     * returns the post request body as a string
     * @param req the request
     * @return String
     * @throws IOException
     */
    public String getPostReqBody(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
