import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

class MarvellousPacker {
    public static void main(String[] args) {
        System.out.println("--------- Marvellous Packer Unpacker CUI Panel --------- ");

        try (Scanner sobj = new Scanner(System.in)) {
            System.out.println("Enter folder name which contains the files that you want to pack: ");
            String folderName = sobj.nextLine();

            File folder = new File(folderName);
            if (!folder.exists() || !folder.isDirectory()) {
                System.out.println("The specified folder does not exist or is not a directory.");
                return;
            }

            System.out.println("Enter the name of the packed file that you want to create: ");
            String packFileName = sobj.nextLine();

            File packFile = new File(packFileName);
            if (packFile.exists()) {
                System.out.println("Warning: The packed file already exists and will be overwritten.");
            }

            try (FileOutputStream fout = new FileOutputStream(packFile)) {
                File[] allFiles = folder.listFiles();
                if (allFiles == null || allFiles.length == 0) {
                    System.out.println("The folder is empty or no files found.");
                    return;
                }

                byte[] buffer = new byte[1024];
                int bytesRead;
                int counter = 0;

                for (File file : allFiles) {
                    String name = file.getName();

                    if (name.endsWith(".txt")) {
                        long fileLength = file.length();
                        String header = String.format("%-100s", name + " " + fileLength);
                        byte[] headerBytes = header.getBytes(StandardCharsets.UTF_8);
                        fout.write(headerBytes);

                        try (FileInputStream fin = new FileInputStream(file)) {
                            while ((bytesRead = fin.read(buffer)) != -1) {
                                fout.write(buffer, 0, bytesRead);
                            }
                        }

                        System.out.println("Packed: " + name + " (" + fileLength + " bytes)");
                        counter++;
                    }
                }

                System.out.println("----- Summary -----");
                System.out.println("Number of files packed successfully: " + counter);
            } catch (IOException e) {
                System.out.println("An error occurred while packing files: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        System.out.println("Thank you for using the Marvellous Packer Unpacker Application");
    }
}
