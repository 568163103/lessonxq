package com.nvss.webmuext.controller;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.SftpFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-14 10:52
 * @Version
 **/
@RequestMapping(value = "/api/v1/upload")
@RestController
public class UploadController {


    @RequestMapping("/image")
    public ResponseEntity<byte[]> uploadImage(@RequestParam(value = "path") String path,
                                              @RequestParam(value = "pos") int pos
                                              ) throws Exception {
         byte[] imageContent = null;

        File file = new File(path);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);

        String [] fileNames =  file.list();
        if (pos < fileNames.length) {
            String fileName = fileNames[pos];

            File tempFile = new File(path + "/" + fileName);

            imageContent = fileToByte(tempFile);
        }
        return new ResponseEntity<>(imageContent, httpHeaders, HttpStatus.OK);
    }

    public static byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }


}
