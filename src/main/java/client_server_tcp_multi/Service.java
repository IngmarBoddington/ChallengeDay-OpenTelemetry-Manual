package client_server_tcp_multi;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

public class Service {

    private static Service instance;

    private Service() {
    }

    public static synchronized Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public String hello() {
        return "Hi!";
    }

    public String time() {
        return Instant.now().toString();
    }

    public String listing(String directory) {

        File dir = new File(directory);
        Collection<String> files  = new ArrayList<String>();

        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            for (File file : listFiles) {
                if (file.isFile() || (file.isDirectory())) {
                    files.add(file.getName());
                }
            }
        }

        return files.toString();
    }
}
