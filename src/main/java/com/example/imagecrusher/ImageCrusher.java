package com.example.imagecrusher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class ImageCrusher {
    private static final int ITERATIONS = 50;
    private static final int BORDER_BLOCK_FRACTION = 5;
    private static final int BORDER_BLOCK = 0;
    private File inputFile;
    private FileInputStream inputStream;
    private String fileName;
    private int fileLength, border;

    public ImageCrusher(File inputFile) throws Exception {
        this.inputFile = inputFile;
        this.inputStream = new FileInputStream(inputFile);
        this.fileName = "crushed_" + inputFile.getName();

        fileLength = (int) inputFile.length();
    }

    public ImageCrusher() {
    }

    public static byte[] crush(byte[] fileContent) throws Exception {
        if (fileContent.length == 0) throw new Exception("file content empty");
        Random rand = new Random();

        System.out.print("0%\r");
        for (int i = 0; i < ITERATIONS; i++) {
            int fileLength = fileContent.length;
            // pick random line (inside of "safe-zone")
            int rdmPointer = rand.nextInt(fileLength-BORDER_BLOCK*2);
            rdmPointer += BORDER_BLOCK;

            // crush
            // swap lines
//             fileContent = swapLines(rdmPointer, rdmPointer + rand.nextInt(fileLength-rdmPointer-border), fileContent);
            // remove lines
            fileContent = removeLines(rdmPointer, fileContent);
            // shift bytes
            //fileContent = byteShift(1, fileContent);

            // create new lines?

            System.out.printf("" + new DecimalFormat("#.0").format((double) 100/ITERATIONS * (i+1)) + "%%\r");
        }
        System.out.println("100,0%");
        return fileContent;
    }

    public void crush(int fileIndex, int fileCount) throws Exception {
        if (this.inputFile == null) throw new Exception("No file selected");
        byte[] fileContent = Files.readAllBytes(inputFile.toPath());
        Random rand = new Random();

        System.out.print("0%\r");
        for (int i = 0; i < ITERATIONS; i++) {
            fileLength = (int) inputFile.length();
            // pick random line (inside of "safe-zone")
            int rdmPointer = rand.nextInt(fileLength-BORDER_BLOCK*2);
            rdmPointer += BORDER_BLOCK;

            // crush
                // swap lines
//             fileContent = swapLines(rdmPointer, rdmPointer + rand.nextInt(fileLength-rdmPointer-border), fileContent);
                // remove lines
            fileContent = removeLines(rdmPointer, fileContent);
                // shift bytes
            //fileContent = byteShift(1, fileContent);

                // create new lines?

            System.out.printf("" + new DecimalFormat("#.0").format((double) 100/ITERATIONS * (i+1)) + "%%        | %s | %d out of %d files done\r", fileName, fileIndex, fileCount);
        }
        System.out.println("100,0%      | " + fileName);
        // save new file in output folder
        File outputFile = new File("crushed files/" + fileName);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(fileContent);
    }

    private byte[] swapLines(long x, long y, byte[] fileContent) {
        int i = (int) x;
        byte[] tmp = new byte[10];
        int tmp_i = 0;
        while (i < fileContent.length - 10) {
            fileContent[i] = fileContent[i+10];
            if (tmp_i < 10) tmp[tmp_i++] = fileContent[i+10];
            i++;
        }
        byte[] newFileContent = new byte[fileContent.length];
        System.arraycopy(fileContent, 0, newFileContent, 0, (int) y);
        System.arraycopy(tmp, 0, newFileContent, (int) y, tmp.length);
        System.arraycopy(fileContent, (int) y, newFileContent, (int) y + tmp.length, fileContent.length - (int) y - tmp.length);
        return fileContent;
    }

    private byte[] byteShift(int shift, byte[] fileContent) {
        for (int i = border; i < fileContent.length; i++) {
            fileContent[i]='7';
        }
        return fileContent;
    }

    private static byte[] removeLines(int pos, byte[] fileContent) throws IOException {
        int i = pos;
        while (i < fileContent.length - 10) {
            fileContent[i] = fileContent[i+10];
            i++;
        }
        return Arrays.copyOfRange(fileContent, 0, fileContent.length-10);
    }

    private void addRandomLine(long pos) {

    }

    public void setInputFile(File inputFile) throws Exception {
        this.inputFile = inputFile;
        this.inputStream = new FileInputStream(inputFile);
        this.fileName = "crushed_" + inputFile.getName();
        fileLength = (int) inputFile.length();
    }
}
