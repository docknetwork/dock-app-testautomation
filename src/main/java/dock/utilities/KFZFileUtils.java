package dock.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class KFZFileUtils {
    private static final Logger log = LoggerFactory.getLogger(WebDriverBuilder.class);

    public String readFileToString(File fileName) {
        String line, fileContent = "";
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                fileContent = fileContent.concat(line);
                fileContent = fileContent.concat("\n");
            }
        }
        catch (IOException ioe) {
            log.error("Error while reading file!", ioe);
            Assert.fail("Error while reading file!");
        }
        finally {
            try {
                br.close();
            }
            catch (IOException ioe) {
                log.error("Error while closing BufferedReader!", ioe);
            }
        }
        return fileContent;
    }

    public void createDir(Path pathToDir) {
        if (!Files.exists(pathToDir)) {
            try {
                Files.createDirectory(pathToDir);
            }
            catch (IOException e) {
                Assert.fail("Could not create directory", e);
            }
        }
    }
}
