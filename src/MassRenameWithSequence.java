import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class MassRenameWithSequence {

    private static void modifyNames() throws IOException {
        AtomicInteger sequence = new AtomicInteger(1);

        Stream<Path> paths = Files.walk(Paths.get("/home/fistro/Pictures/Fotos"));

        paths.filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        renameKeepingExtension(sequence, path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void renameKeepingExtension(AtomicInteger sequence, Path path) throws IOException {
        int dotLocation = path.toString().lastIndexOf(".");
        String currentExtension = path.toString().substring(dotLocation);
        Files.move(path, path.resolveSibling("Foto_" + sequence.incrementAndGet() + currentExtension.toLowerCase()));
    }

    public static void main(String[] args) throws IOException {
        modifyNames();
    }
}
