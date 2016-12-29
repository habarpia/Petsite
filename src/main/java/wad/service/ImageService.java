package wad.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wad.domain.FileObject;
import wad.domain.Image;
import wad.repository.FileObjectRepository;
import wad.repository.ImageRepository;
//import wad.service.imaging.Thumbnailer;

@Service
public class ImageService {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Async
    public void add(Image image, String mediaType, String filename, byte[] data) throws IOException {
        FileObject original = new FileObject();
        original.setContentType(mediaType);
        original.setContent(data);
        original.setName(filename);
        original.setContentLength(new Long(data.length));

        original = fileObjectRepository.save(original);

        image.setOriginal(original);

        imageRepository.save(image);
    }
    
    
}
