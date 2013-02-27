/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.oj.action.file;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;

import java.io.*;
import java.util.List;

/**
 * Action for file upload service
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 5
 */
public class FileUploadAction extends BaseAction {

    private static final int BUFFER_SIZE = 2048;
    private List<File> uploadFile;
    private List<String> uploadFileContentType;
    private List<String> uploadFileFileName;

    private String savePath;

    public List<File> getUploadFile() {
        return uploadFile;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUploadFile(List<File> uploadFile) {
        this.uploadFile = uploadFile;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<String> getUploadFileContentType() {
        return uploadFileContentType;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUploadFileContentType(List<String> uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public List<String> getUploadFileFileName() {
        return uploadFileFileName;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUploadFileFileName(List<String> uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    public String getSavePath() {
        return servletContext.getRealPath(savePath);
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * Upload files and return all uploaded files' absolute path.
     * <p/>
     * We should set ourselves' type filter for this upload action.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String[] uploadFile() {
        // TODO check type and size
        File dir = new File(getSavePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        List<File> files = getUploadFile();
        String[] result = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            result[i] = getSavePath() + "/" + getUploadFileFileName().get(i);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(result[i]);
                FileInputStream fileInputStream = new FileInputStream(getUploadFile().get(i));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER_SIZE);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                int len;
                while ((len = bufferedInputStream.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, len);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                // this file cannot be uploaded.
                e.printStackTrace();
                result[i] = null;
            }
        }
        return result;
    }
}
