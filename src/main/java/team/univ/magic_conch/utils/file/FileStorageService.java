package team.univ.magic_conch.utils.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService implements StorageService{

    @Value("${custom.file.path}")
    private String path;

    @Override
    public String save(byte[] bytes, String filename, String location) {
        UUID uuid = UUID.randomUUID();
        String uniqueName = uuid.toString() + filename;
        try(FileOutputStream fos = new FileOutputStream(location + "/" + uniqueName)) {
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path + "/" + uniqueName;
    }
}
