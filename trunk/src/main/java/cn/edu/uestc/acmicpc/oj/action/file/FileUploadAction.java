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
import cn.edu.uestc.acmicpc.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Action for file upload service
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class FileUploadAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8954368803487410069L;
	private List<File> uploadFile;
	private List<String> uploadFileContentType;
	private List<String> uploadFileFileName;

	private String savePath;

	protected List<File> getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(List<File> uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<String> getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(List<String> uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	protected List<String> getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(List<String> uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	protected String getSavePath() {
		return savePath;
	}

	protected void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * Upload files and return all uploaded files' absolute path.
	 * <p/>
	 * We should set ourselves' type filter for this upload action.
	 */
	protected String[] uploadFile() {
		// TODO check type and size
		File dir = new File(getSavePath());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		List<File> files = getUploadFile();
		String[] result = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			try {
				result[i] = getSavePath() + "/"
						+ getUploadFileFileName().get(i);
				FileUtil.saveToFile(
						new FileInputStream(getUploadFile().get(i)),
						new FileOutputStream(result[i]));
			} catch (IOException e) {
				// this file cannot be uploaded.
				e.printStackTrace();
				result[i] = null;
			}
		}
		return result;
	}
}
