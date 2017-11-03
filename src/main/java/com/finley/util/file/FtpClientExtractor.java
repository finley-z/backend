package com.finley.util.file;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public interface FtpClientExtractor {
	public void doInFtp(FTPClient client) throws IOException;
}
