package com.ragtagger.brag.utils;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 27-02-2018.
 */


public class FileUtils {
    public static final long GB = 1024 * 1024 * 1024;
    public static final long MB = 1024 * 1024;
    public static final long KB = 1024;
    public final static String FILE_EXTENSION_SEPARATOR = ".";
    private static final String FILENAME_REGIX = "^[^\\\\/?\\\"*:<>\\\\\u0005]{1,255}$";

    private FileUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * delete files or empty folder
     *
     * @param file return result weather file deleted or not
     */
    public static boolean deleteFile(File file) {
        return file.delete();
    }

    /**
     * @param file root directory you want to delete containing other files
     */
    public static void deleteAllFile(File file) {
        if (!file.exists()) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteAllFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * Rename files and folders
     *
     * @param file        File
     * @param newFileName new file name want to give
     *                    return results weather file renamed or not
     */
    public static boolean renameFile(File file, String newFileName) {
        if (newFileName.matches(FILENAME_REGIX)) {
            File newFile = null;
            if (file.isDirectory()) {
                newFile = new File(file.getParentFile(), newFileName);
            } else {
                String temp = newFileName
                        + file.getName().substring(
                        file.getName().lastIndexOf('.'));
                newFile = new File(file.getParentFile(), temp);
            }
            if (file.renameTo(newFile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get file size
     * if path is null or empty, return -1
     * if path exist and it is a file, return file size, else return -1
     * param Path file path
     *
     * @return returns the length of this file in bytes. returns -1 if the file
     * does not exist.
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * Get file size
     *
     * @param file for you want to get size
     *             return the string of file size
     */
    public static String getFileSize(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int length = fis.available();
            if (length >= GB) {
                return String.format("%.2f GB", length * 1.0 / GB);
            } else if (length >= MB) {
                return String.format("%.2f MB", length * 1.0 / MB);
            } else {
                return String.format("%.2f KB", length * 1.0 / KB);
            }
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e.getCause());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                AppLogger.e(e.getMessage(), e.getCause());
            }
        }
        return "0 KB";
    }

    /**
     * read file
     * <p/>
     * param FilePath the file path
     *
     * @param charsetName The name of a supported {@link java.nio.charset.Charset
     *                    charset} i.e UTF-8, UTF-16 etc,
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (!file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * @param filePath file path where file exist
     * @param content  to write on files
     * @param append   if true, write to the end of file, else clear
     *                 content of file and write into it
     * @return return false if content is empty, otherwise return true
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content,
                                    boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * @param contentList contentList
     * @param append      if true, write to the end of file, else clear
     *                    content of file and write into it
     * @return return false if contentList is empty, otherwise return true
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, List<String> contentList,
                                    boolean append) {
        if (contentList == null || contentList.size() < 1) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            //int i = 0;
            for (String line : contentList) {
                //if (i++ > 0) {
                fileWriter.write("\r\n");
                //}
                fileWriter.write(line);
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * @param filePath the file path where file exist
     * @param content  content
     *                 return Execution results
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    /**
     * @param filePath    the file path where file exist
     * @param contentList contentList
     *                    return Execution results
     */
    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, contentList, false);
    }

    /**
     * @param filePath the file path where file exist
     * @param stream   InputStream
     *                 return Execution results
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * @param filePath the file path where file exist.
     * @param stream   content to write on file, in formate of the input stream
     * @param append   if true, then bytes will be written to the end of
     *                 the file
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream,
                                    boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream,
                append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file   the file to be opened for writing
     * @param stream data to write in form of input stream
     * @return
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream data to write in the form of input stream
     * @param append if true, then bytes will be written to the end of
     *               the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream,
                                    boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * @param sourceFilePath from where you want copy file data
     * @param destFilePath   destination file path
     *                       return Execution results
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * convert stream in to byte[] format
     *
     * @param inStream stream which you want to convert to byte[] format
     * @return data byte[] form
     */
    public static final byte[] input2byte(InputStream inStream) {
        if (inStream == null)
            return null;
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return swapStream.toByteArray();
    }

    /**
     * @param filePath    the file path where file exist to be read
     * @param charsetName The name of a supported {@link java.nio.charset.Charset
     *                    charset} i.e UTF-8, UTF-16 etc,
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath,
                                              String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (!file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? filePath : filePath.substring(filePos + 1);
    }

    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? "" : filePath.substring(0, filePos);
    }

    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * @param filePath the file path to check weather it is exist or not
     *                 return The existence of the file
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * @param directoryPath folder path to check weather this folder exist or not
     *                      return true if exist otherwise return false
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * delete file or direkctory
     * if path is null or empty, return true
     * if path not exist, return true
     * if path exist, delete recursion. return true
     * param Path file path
     * return Whether deleted successfully
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }


}
