import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MoveAllFileFromFolderTreeToSingleFolder {

    private static List<Path> getPaths() throws IOException {
        return Files.find(Paths.get("/media/fistro/Arcones/Mis Imágenes"), Integer.MAX_VALUE, (p, bfa) -> bfa.isRegularFile()).collect(Collectors.toList());
    }

    private static void copyFilesToDestinationExceptFor(List<String> regexExceptions, List<Path> paths) throws IOException {
        for (Path path : paths) {
            boolean matches = matchesWithExceptions(regexExceptions, path);
            moveFileToDestination(path, matches);
        }
    }

    private static boolean matchesWithExceptions(List<String> regexExceptions, Path path) {
        boolean matches = false;
        for (String regex : regexExceptions) {
            if (path.toString().matches(regex)) {
                matches = true;
            }
        }
        return matches;
    }

    private static void moveFileToDestination(Path path, boolean matches) throws IOException {
        if (!matches) {
            String currentPath = path.toString();
            int lastSlashIndex = currentPath.lastIndexOf("/");
            String filename = currentPath.substring(lastSlashIndex, currentPath.length());

            Files.move(path, Paths.get("/home/fistro/Pictures/OLD2" + filename), REPLACE_EXISTING);
        }
    }

    public static void main(String[] args) throws IOException {
        copyFilesToDestinationExceptFor(List.of(".*db"), getPaths());
    }
}
