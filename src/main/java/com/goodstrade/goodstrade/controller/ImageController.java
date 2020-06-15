package com.goodstrade.goodstrade.controller;

import com.goodstrade.goodstrade.entity.Image;
import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;


    @Value("${file.path.upload.main}")
    private String fileUploadPath;

    @PostMapping("/fileupload/{type}")
    public ResponseEntity uploadSingleFile(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes, MultipartHttpServletRequest request, @PathVariable("type") String type) throws IOException, ServletException {


        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            User userAccount = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String rootPath = request.getServletContext().getRealPath("/");
            Date today = Calendar.getInstance().getTime();
            // create a date "formatter" (the date format we want)
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("hhmmss");
            // create a new String using the date format we want
            String folderDate = dateFormatter.format(today);
            String folderTime = timeFormatter.format(today);
            String filePath = fileUploadPath + File.separator + userAccount.getUsername() + File.separator + type + File.separator + folderDate + File.separator;
            Path dirPath = Paths.get(filePath);
            if (!Files.exists(dirPath))
                try {
                    Files.createDirectories(dirPath);
                } catch (IOException e) {
                    // fail to create directory
                    e.printStackTrace();
                }

            Path path = Paths.get(filePath+folderTime+file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

//            String fileId = (String) request.getSession().getAttribute(type);

            List<File> newFiles = Files.walk(Paths.get(filePath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            File newFile = new File(filePath+folderTime+file.getOriginalFilename());
            MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

            Image fileUpload = new Image();
            fileUpload.setPath("/filedownload?imagepath=" + newFile.getPath().replace("\\", "/"));
            imageRepository.save(fileUpload);
            return new ResponseEntity<>(fileUpload, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/filedownload")
    public ResponseEntity getImage(@RequestParam String imagepath) throws IOException {
        if(!imagepath.startsWith(fileUploadPath) && !!imagepath.startsWith(fileUploadPath))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        byte[] bytes = Files.readAllBytes(new File(imagepath).toPath());

        // Set headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }
}
